/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

import java.util.Map;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM="/vets/createOrUpdateVetForm";

	private final VetService vetService;

	private final SpecialtyService specialtyService;

	@Autowired
	public VetController(VetService clinicService,SpecialtyService specialtyService) {
		this.vetService = clinicService;
		this.specialtyService = specialtyService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		var vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		var vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		var vet = new Vet();
		model.put("vet", vet);
		
		return "vets/createOrUpdateVetForm";
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return "vets/createOrUpdateVetForm";
		}else {
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}

	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateOwnerForm(@PathVariable("vetId") int vetId, Model model) {
		var vet = this.vetService.findById(vetId);
		model.addAttribute(vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateOwnerForm(@Valid Vet vet, BindingResult result,
			@PathVariable("vetId") int vetId) {
		if (result.hasErrors()) {
			return "/vets/editVetspecialties";
		}
		else {
			vet.setId(vetId);
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}

	@GetMapping(value = "/vets/{vetId}/specialties/edit")
	public String editVetSpecialtiesFormInit(@PathVariable("vetId") Integer vetId, Map<String, Object> model) {
		var s = new Specialty();
		List<Specialty> allSpecialties = specialtyService.getAllSpecialties();
		var v = vetService.findById(vetId);
		model.put("specialty", s);
		model.put("specialtiesList", allSpecialties);
		model.put("vet", v);
		
		return "vets/editVetspecialties";
	}
	
	@PostMapping(value = "/vets/{vetId}/specialties/edit")
	public String editVetSpecialtiesFormProcess(@PathVariable("vetId") Integer vetId, @Valid Specialty specialty, Map<String, Object> model, BindingResult result) {
		if (result.hasErrors()) {
			return "vets/editVetspecialties";
		}else {
			var v = vetService.findById(vetId);
			var s = specialtyService.getSpecialtyByName(specialty.getName());
			v.addSpecialty(s);
			vetService.saveVet(v);
			
			return "redirect:/vets";
		}
	}
	

	@GetMapping(value = "/vets/{vetId}/delete")
    public String deleteVisit(@PathVariable("vetId") int vetId){
        this.vetService.deleteVet(vetId);
        return "redirect:/vets";
    }

}
