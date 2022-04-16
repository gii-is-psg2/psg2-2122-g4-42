package org.springframework.samples.petclinic.hotel;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.ehcache.shadow.org.terracotta.offheapstore.paging.OffHeapStorageArea.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class HotelController {

	private final HotelService hotelService;
	private final PetService petService;


	@Autowired
	public HotelController(HotelService hotelService, PetService petService) {
		this.hotelService = hotelService;
		this.petService = petService;
		
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("hotel")
	public Hotel loadPetWithHotel(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		Hotel hotel = new Hotel();
		pet.addHotel(hotel);
		return hotel;
	}

	@ModelAttribute("rooms")
	public Collection<RoomsType> populateRoomsTypes() {
		return this.hotelService.findRoomsTypes();
	}

	@GetMapping(value = "/owners/*/pets/{petId}/hotel/new")
	public String initNewHotelForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		
		return "pets/createOrUpdateHotelForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/hotel/new")
	public String processNewHotelForm(@Valid Hotel hotel, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateHotelForm";		
		} else if(hotel.getDate1().isAfter(hotel.getDate2())) {
			result.rejectValue("date2", "Fecha de salida anterior a la fecha de entrada", "Fecha de salida anterior a la fecha de entrada.");
			return "pets/createOrUpdateHotelForm";
		}
		else  {
			this.hotelService.saveHotel(hotel);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/hotels")
	public String showHotels(@PathVariable int petId, Map<String, Object> model) {
		model.put("hotels", this.petService.findPetById(petId).getHotels());
		return "hotelList";
	}

}
