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
package org.springframework.samples.petclinic.pet;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// import okhttp3.OkHttpClient;
// import okhttp3.Request;
// import okhttp3.Response;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private static final String VIEWS_PETS_NOTIF_FORM = "pets/notifPetForm";

	private final PetService petService;
	private final OwnerService ownerService;

	@Autowired
	public PetController(PetService petService, OwnerService ownerService) {
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.ownerService.findOwnerById(ownerId);
	}

	/*
	 * @ModelAttribute("pet")
	 * public Pet findPet(@PathVariable("petId") Integer petId) {
	 * Pet result=null;
	 * if(petId!=null)
	 * result=this.clinicService.findPetById(petId);
	 * else
	 * result=new Pet();
	 * return result;
	 * }
	 */

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping(value = "/pets/{petId}/notif")
	public String initNotifForm(@PathVariable("petId") int petId, Owner owner, ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		model.put("notification", new Notification());
		return VIEWS_PETS_NOTIF_FORM;
	}

	@PostMapping(value = "/pets/{petId}/notif")
	public String processNotifForm(@PathVariable("petId") int petId, Owner owner, @Valid Notification msg,
			BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("notification", msg);
			return VIEWS_PETS_NOTIF_FORM;
		} else {
			String to = owner.getTelephone();
			String body = petService.findPetById(petId).getName().toUpperCase() + " - " + msg.getMessage();
			String from = "678912345"; // PetClinic's telephone

			/* IF WE USE THE API
			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://twilio-sms.p.rapidapi.com/2010-04-01/Accounts/%7BAccountSid%7D/Messages.json?to="+to+"&body="+body+"&from="+from)
					.post(null)
					.addHeader("X-RapidAPI-Host", "twilio-sms.p.rapidapi.com")
					.addHeader("X-RapidAPI-Key", "517571cf85msh45d17fba27da8dep11b959jsne7f8befb75ff") // here would be the api key if we had registered and purchased it
					.build();

			Response response = client.newCall(request).execute();

			if(response.code() == 200) return "redirect:/owners/{ownerId}";
			else return VIEWS_PETS_NOTIF_FORM;
			*/

			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				owner.addPet(pet);
				this.petService.savePet(pet);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		Pet pet = this.petService.findPetById(petId);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	/**
	 *
	 * @param pet
	 * @param result
	 * @param petId
	 * @param model
	 * @param owner
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, @PathVariable("petId") int petId,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			Pet petToUpdate = this.petService.findPetById(petId);
			BeanUtils.copyProperties(pet, petToUpdate, "id", "owner", "visits");
			try {
				this.petService.savePet(petToUpdate);
			} catch (DuplicatedPetNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
			}
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/pets/{petId}/delete")
	public String deletePet(@PathVariable("petId") int petId) {
		this.petService.deletePet(petId);
		return "redirect:/owners/{ownerId}";
	}

	@GetMapping(value = "pets/{petId}/visits/{visitId}/delete")
	public String deleteVisit(@PathVariable("visitId") int visitId) {
		this.petService.deleteVisit(visitId);
		return "redirect:/owners/{ownerId}";
	}

}
