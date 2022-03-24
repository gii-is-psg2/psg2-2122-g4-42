package org.springframework.samples.petclinic.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
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

import javax.validation.Valid;

@Controller
public class AdoptionController {

	private final AdoptionService adoptionService;

    private final RequestAdoptionService requestAdoptionService;

    private final PetService petService;

	@Autowired
	public AdoptionController(AdoptionService adoptionService, RequestAdoptionService requestAdoptionService, PetService petService) {
		this.adoptionService = adoptionService;
        this.requestAdoptionService = requestAdoptionService;
        this.petService = petService;
	}

	@GetMapping(value = "/adoptions" )
	public String showAdoptionList(Map<String, Object> model) {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String user = authentication.getName();
		List<Adoption> adoptions = this.adoptionService.findAllAdoptions();
        List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
		model.put("adoptions", adoptions);
        model.put("requestAdoptions", requestAdoptions);
        
		// model.put("user", user);
		return "adoptions/adoptionList";
	}

	@GetMapping(value =  "/adoptions/{adoptionId}/requestList" )
	public String showAdoptionRequestList(Map<String, Object> model, @PathVariable("adoptionId") int adoptionId) {
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String user = authentication.getName();
		Collection<RequestAdoption> requestAdoptions = this.adoptionService.findAdoptionById(adoptionId).getRequestAdoptions();
		model.put("requestAdoptions", requestAdoptions);
        // List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        // model.put("requestAdoptions", requestAdoptions);
		// model.put("user", user);
		return "adoptions/adoptionRequestList";
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePet() {
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


	// @PostMapping(value = "/vets/new")
	// public String processCreationForm(@Valid Vet vet, BindingResult result, Map<String, Object> model) {
	// 	if (result.hasErrors()) {
	// 		model.put("vet", vet);
	// 		return "vets/createOrUpdateVetForm";
	// 	}else {
	// 		this.vetService.saveVet(vet);
	// 		return "redirect:/vets";
	// 	}
	// }

	// @GetMapping(value = "/vets/{vetId}/edit")
	// public String initUpdateOwnerForm(@PathVariable("vetId") int vetId, Model model) {
	// 	Vet vet = this.vetService.findById(vetId);
	// 	model.addAttribute(vet);
	// 	return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	// }

	// @PostMapping(value = "/vets/{vetId}/edit")
	// public String processUpdateOwnerForm(@Valid Vet vet, BindingResult result,
	// 		@PathVariable("vetId") int vetId) {
	// 	if (result.hasErrors()) {
	// 		return "/vets/editVetspecialties";
	// 	}
	// 	else {
	// 		vet.setId(vetId);
	// 		this.vetService.saveVet(vet);
	// 		return "redirect:/vets";
	// 	}
	// }

	// @GetMapping(value = "/vets/{vetId}/specialties/edit")
	// public String editVetSpecialtiesFormInit(@PathVariable("vetId") Integer vetId, Map<String, Object> model) {
	// 	Specialty s = new Specialty();
	// 	List<Specialty> allSpecialties = specialtyService.getAllSpecialties();
	// 	Vet v = vetService.findById(vetId);
	// 	model.put("specialty", s);
	// 	model.put("specialtiesList", allSpecialties);
	// 	model.put("vet", v);
		
	// 	return "vets/editVetspecialties";
	// }
	
	// @PostMapping(value = "/vets/{vetId}/specialties/edit")
	// public String editVetSpecialtiesFormProcess(@PathVariable("vetId") Integer vetId, @Valid Specialty specialty, Map<String, Object> model, BindingResult result) {
	// 	if (result.hasErrors()) {
	// 		return "vets/editVetspecialties";
	// 	}else {
	// 		Vet v = vetService.findById(vetId);
	// 		Specialty s = specialtyService.getSpecialtyByName(specialty.getName());
	// 		v.addSpecialty(s);
	// 		vetService.saveVet(v);
			
	// 		return "redirect:/vets";
	// 	}
	// }
	

	// @GetMapping(value = "/vets/{vetId}/delete")
    // public String deleteVisit(@PathVariable("vetId") int vetId){
    //     this.vetService.deleteVet(vetId);
    //     return "redirect:/vets";
    // }

}
