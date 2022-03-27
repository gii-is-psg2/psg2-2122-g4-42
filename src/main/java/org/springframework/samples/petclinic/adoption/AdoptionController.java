package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
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

        List<Adoption> adoptionsAvailables = new ArrayList<Adoption>();
        adoptionsAvailables.addAll(allAdoptions);
        List<Adoption> myAdoptions = new ArrayList<Adoption>();
        Set<RequestAdoption> myRequestAdoptions = new HashSet<RequestAdoption>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals("owner")) {
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals("owner")) {
                Owner owner = this.ownerService.findByUsername(username);
                List<Pet> myPets = this.ownerService.findOwnerById(owner.getId()).getPets();
                List<Pet> petsForAdoption = new ArrayList<Pet>();
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
        Adoption adoption = new Adoption();
        model.put("adoption", adoption);

        return "adoptions/createAdoptionForm";
    }

    @PostMapping(value = "/adoptions/new")
    public String createRequestForm(@Valid Adoption adoption,
            BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            model.put("adoption", adoption);
            return "adoptions/createRequestAdoptionForm";
        } else {
            this.adoptionService.saveAdoption(adoption);
            return "redirect:/adoptions";
        }
    }

    @GetMapping(value = "/adoptions/acceptRequest/{requestAdoptionId}")
    public String acceptRequest(@PathVariable("requestAdoptionId") int requestAdoptionId) {
        RequestAdoption requestAdoption = this.requestAdoptionService.findRequestAdoptionById(requestAdoptionId);
        requestAdoption.setStatus(Status.APPROVED);
        Adoption adoption = this.adoptionService.findAllAdoptions().stream()
                .filter(a -> a.getRequestAdoptions().contains(requestAdoption))
                .collect(Collectors.toList()).get(0);
        Owner newOwner = requestAdoption.getOwner();
        Pet pet = adoption.getPet();
        Owner oldOwner = this.ownerService.findAllOwners().stream().filter(owner -> owner.getPets().contains(pet))
                .collect(Collectors.toList()).get(0);
        oldOwner.removePet(pet);
        newOwner.addPet(pet);
        this.ownerService.saveOwner(oldOwner);
        this.ownerService.saveOwner(newOwner);
        this.adoptionService.deleteAdoption(adoption.getId());
        return "redirect:/adoptions";
    }

    @GetMapping(value = "/adoptions/denyRequest/{requestAdoptionId}")
    public String denyRequest(@PathVariable("requestAdoptionId") int requestAdoptionId) {
        RequestAdoption requestAdoption = this.requestAdoptionService.findRequestAdoptionById(requestAdoptionId);
        requestAdoption.setStatus(Status.DENIED);
        this.requestAdoptionService.saveRequestAdoption(requestAdoption);
        return "redirect:/adoptions";
    }

}
