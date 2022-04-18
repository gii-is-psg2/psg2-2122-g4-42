package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
public class AdoptionController {

    private final AdoptionService adoptionService;

    private final RequestAdoptionService requestAdoptionService;

    private final PetService petService;

    private final OwnerService ownerService;

    private final UserService userService;

    final String ownerStr = "owner";

    private static final String VIEWS_REQUEST_ADOPTION_CREATE_FORM = "adoptions/createRequestAdoptionForm";

    private static final String VIEWS_REDIRECT_ADOPTION_LIST = "redirect:/adoptions";

    @Autowired
    public AdoptionController(AdoptionService adoptionService, RequestAdoptionService requestAdoptionService,
            PetService petService, OwnerService ownerService, UserService userService) {
        this.adoptionService = adoptionService;
        this.requestAdoptionService = requestAdoptionService;
        this.petService = petService;
        this.ownerService = ownerService;
        this.userService = userService;
    }

    @GetMapping(value = "/adoptions")
    public String showAdoptionList(Map<String, Object> model) {
        List<Adoption> allAdoptions = this.adoptionService.findAllAdoptions();

        List<Adoption> adoptionsAvailables = new ArrayList<>();
        adoptionsAvailables.addAll(allAdoptions);
        List<Adoption> myAdoptions = new ArrayList<>();
        Set<RequestAdoption> myRequestAdoptions = new HashSet<>();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals(ownerStr)) {
                Owner owner = this.ownerService.findByUsername(username);
                List<Pet> myPets = this.ownerService.findOwnerById(owner.getId()).getPets();
                for (Adoption adoption : allAdoptions) {
                    if (myPets.contains(adoption.getPet())) {
                        myAdoptions.add(adoption);
                        adoptionsAvailables.remove(adoption);
                    }
                }
                myRequestAdoptions = this.ownerService.findOwnerById(owner.getId()).getRequestAdoptions();
            }
        }

        model.put("adoptionsAvailables", adoptionsAvailables);
        model.put("myAdoptions", myAdoptions);
        model.put("myRequestAdoptions", myRequestAdoptions);

        return "adoptions/adoptionList";
    }

    @GetMapping(value = "/adoptions/{adoptionId}/requestList")
    public String showAdoptionRequestList(Map<String, Object> model, @PathVariable("adoptionId") int adoptionId) {
        List<RequestAdoption> requestAdoptions = this.adoptionService.findAdoptionById(adoptionId).getRequestAdoptions()
                .stream().collect(Collectors.toList());
        Collections.sort(requestAdoptions, new Comparator<RequestAdoption>() {
            public int compare(RequestAdoption o1, RequestAdoption o2) {
                return o2.getStatus().toString().compareTo(o1.getStatus().toString());
            }
        });
        model.put("requestAdoptions", requestAdoptions);
        return "adoptions/adoptionRequestList";
    }

    @ModelAttribute("pets")
    public Collection<Pet> populatePet() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals(ownerStr)) {
                var owner = this.ownerService.findByUsername(username);
                List<Pet> myPets = this.ownerService.findOwnerById(owner.getId()).getPets();
                List<Pet> petsForAdoption = new ArrayList<>();
                petsForAdoption.addAll(myPets);
                for (Adoption adoption : this.adoptionService.findAllAdoptions()) {
                    if (myPets.contains(adoption.getPet()))
                        petsForAdoption.remove(adoption.getPet());
                }
                return petsForAdoption;
            }
        }
        return this.petService.findAllPets();
    }

    @GetMapping(value = "/adoptions/new")
    public String initCreationForm(Map<String, Object> model) {
        var adoption = new Adoption();
        model.put("adoption", adoption);

        return "adoptions/createAdoptionForm";
    }

    @PostMapping(value = "/adoptions/new")
    public String createAdoptionForm(@Valid Adoption adoption,
            BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_REQUEST_ADOPTION_CREATE_FORM;
        } else {
            this.adoptionService.saveAdoption(adoption);
            return VIEWS_REDIRECT_ADOPTION_LIST;
        }
    }

    @GetMapping(value = "/adoptions/acceptRequest/{requestAdoptionId}")
    public String acceptRequest(@PathVariable("requestAdoptionId") int requestAdoptionId) {
        var requestAdoption = this.requestAdoptionService.findRequestAdoptionById(requestAdoptionId);
        requestAdoption.setStatus(Status.APPROVED);
        this.requestAdoptionService.saveRequestAdoption(requestAdoption);
        var adoption = this.adoptionService.findAllAdoptions().stream()
                .filter(a -> a.getRequestAdoptions().contains(requestAdoption))
                .collect(Collectors.toList()).get(0);
        var newOwner = requestAdoption.getOwner();
        var pet = adoption.getPet();
        newOwner.addPet(pet);
        this.ownerService.saveOwner(newOwner);
        this.adoptionService.deleteAdoption(adoption.getId());
        return VIEWS_REDIRECT_ADOPTION_LIST;
    }

    @GetMapping(value = "/adoptions/denyRequest/{requestAdoptionId}")
    public String denyRequest(@PathVariable("requestAdoptionId") int requestAdoptionId) {
        var requestAdoption = this.requestAdoptionService.findRequestAdoptionById(requestAdoptionId);
        requestAdoption.setStatus(Status.DENIED);
        this.requestAdoptionService.saveRequestAdoption(requestAdoption);
        return VIEWS_REDIRECT_ADOPTION_LIST;
    }

    @GetMapping(value = "/adoptions/{adoptionId}/request")
    public String initCreationFormRequestAdoption(Map<String, Object> model) {
        var requestAdoption = new RequestAdoption();
        model.put("requestAdoption", requestAdoption);

        return VIEWS_REQUEST_ADOPTION_CREATE_FORM;
    }

    @PostMapping(value = "/adoptions/{adoptionId}/request")
    public String createRequestForm(@PathVariable("adoptionId") int adoptionId, @Valid RequestAdoption requestAdoption,
            BindingResult result) {
        var adoption = this.adoptionService.findAdoptionById(adoptionId);
        Collection<RequestAdoption> requests = adoption.getRequestAdoptions();

        Owner owner = null;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals(ownerStr)) {
                owner = this.ownerService.findByUsername(username);
            }
        }

        if (result.hasErrors()) {
            if (requestAdoption.getStatus() == null) {
                requestAdoption.setStatus(Status.WAITING);
                requestAdoption.setOwner(owner);
                requestAdoption.setAdoption(adoption);
                requests.add(requestAdoption);
                adoption.setRequestAdoptions(requests);
                this.requestAdoptionService.saveRequestAdoption(requestAdoption);
                return VIEWS_REDIRECT_ADOPTION_LIST;
            }
            return VIEWS_REQUEST_ADOPTION_CREATE_FORM;
        } else {
            requests.add(requestAdoption);
            adoption.setRequestAdoptions(requests);
            this.requestAdoptionService.saveRequestAdoption(requestAdoption);
            return VIEWS_REDIRECT_ADOPTION_LIST;
        }
    }

}
