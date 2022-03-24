package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;

import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.validation.Valid;

@Controller
public class RequestAdoptionController {

    private final AdoptionService adoptionService;

    private final RequestAdoptionService requestAdoptionService;

    @Autowired
    public RequestAdoptionController(AdoptionService adoptionService, RequestAdoptionService requestAdoptionService) {
        this.adoptionService = adoptionService;
        this.requestAdoptionService = requestAdoptionService;
    }

    // @GetMapping(value = { "/adoptions" })
    // public String showRequestAdoptionList(Map<String, Object> model) {
    // // Authentication authentication =
    // // SecurityContextHolder.getContext().getAuthentication();
    // // String user = authentication.getName();
    // List<RequestAdoption> requestAdoptions =
    // this.requestAdoptionService.findAllRequestAdoptions();
    // model.put("requestAdoptions", requestAdoptions);
    // // model.put("user", user);
    // return "adoptions/adoptionList";
    // }

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
        if (result.hasErrors()) {
            if (requestAdoption.getStatus() == null) {
                requestAdoption.setStatus(Status.WAITING);
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

    // @GetMapping(value = "/vets/{vetId}/edit")
    // public String initUpdateOwnerForm(@PathVariable("vetId") int vetId, Model
    // model) {
    // Vet vet = this.vetService.findById(vetId);
    // model.addAttribute(vet);
    // return VIEWS_VET_CREATE_OR_UPDATE_FORM;
    // }

    // @PostMapping(value = "/vets/{vetId}/edit")
    // public String processUpdateOwnerForm(@Valid Vet vet, BindingResult result,
    // @PathVariable("vetId") int vetId) {
    // if (result.hasErrors()) {
    // return "/vets/editVetspecialties";
    // }
    // else {
    // vet.setId(vetId);
    // this.vetService.saveVet(vet);
    // return "redirect:/vets";
    // }
    // }

    // @GetMapping(value = "/vets/{vetId}/specialties/edit")
    // public String editVetSpecialtiesFormInit(@PathVariable("vetId") Integer
    // vetId, Map<String, Object> model) {
    // Specialty s = new Specialty();
    // List<Specialty> allSpecialties = specialtyService.getAllSpecialties();
    // Vet v = vetService.findById(vetId);
    // model.put("specialty", s);
    // model.put("specialtiesList", allSpecialties);
    // model.put("vet", v);

    // return "vets/editVetspecialties";
    // }

    // @PostMapping(value = "/vets/{vetId}/specialties/edit")
    // public String editVetSpecialtiesFormProcess(@PathVariable("vetId") Integer
    // vetId, @Valid Specialty specialty, Map<String, Object> model, BindingResult
    // result) {
    // if (result.hasErrors()) {
    // return "vets/editVetspecialties";
    // }else {
    // Vet v = vetService.findById(vetId);
    // Specialty s = specialtyService.getSpecialtyByName(specialty.getName());
    // v.addSpecialty(s);
    // vetService.saveVet(v);

    // return "redirect:/vets";
    // }
    // }

    // @GetMapping(value = "/vets/{vetId}/delete")
    // public String deleteVisit(@PathVariable("vetId") int vetId){
    // this.vetService.deleteVet(vetId);
    // return "redirect:/vets";
    // }

}
