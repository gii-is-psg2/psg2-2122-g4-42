package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

@Controller
public class RequestAdoptionController {

    private final AdoptionService adoptionService;

    private final RequestAdoptionService requestAdoptionService;

    private final UserService userService;

    private final OwnerService ownerService;

    @Autowired
    public RequestAdoptionController(AdoptionService adoptionService, RequestAdoptionService requestAdoptionService,
            UserService userService, OwnerService ownerService) {
        this.adoptionService = adoptionService;
        this.requestAdoptionService = requestAdoptionService;
        this.userService = userService;
        this.ownerService = ownerService;
    }

    @GetMapping(value = "/adoptions/{adoptionId}/request")
    public String initCreationForm(Map<String, Object> model) {
        RequestAdoption requestAdoption = new RequestAdoption();
        model.put("requestAdoption", requestAdoption);

        return "adoptions/createRequestAdoptionForm";
    }

    @PostMapping(value = "/adoptions/{adoptionId}/request")
    public String createRequestForm(@PathVariable("adoptionId") int adoptionId, @Valid RequestAdoption requestAdoption,
            BindingResult result, Map<String, Object> model) {
        Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
        Collection<RequestAdoption> requests = adoption.getRequestAdoptions();

        Owner owner = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = this.userService.findByUsername(username);
        Set<Authorities> authorities = user.getAuthorities();
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals("owner")) {
                owner = this.ownerService.findByUsername(username);
            }
        }

        if (result.hasErrors()) {
            if (requestAdoption.getStatus() == null) {
                requestAdoption.setStatus(Status.WAITING);
                requestAdoption.setOwner(owner);
                requests.add(requestAdoption);
                adoption.setRequestAdoptions(requests);
                this.requestAdoptionService.saveRequestAdoption(requestAdoption);
                return "redirect:/adoptions";
            }
            model.put("requestAdoption", requestAdoption);
            return "adoptions/createRequestAdoptionForm";
        } else {
            requests.add(requestAdoption);
            adoption.setRequestAdoptions(requests);
            this.requestAdoptionService.saveRequestAdoption(requestAdoption);
            return "redirect:/adoptions";
        }
    }

}
