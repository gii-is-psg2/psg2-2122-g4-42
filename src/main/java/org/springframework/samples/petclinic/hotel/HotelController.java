package org.springframework.samples.petclinic.hotel;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	private static final String VIEWS_HOTEL_CREATE_OR_UPDATE_FORM="pets/createOrUpdateHotelForm";

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
		var pet = this.petService.findPetById(petId);
		var hotel = new Hotel();
		pet.addHotel(hotel);
		return hotel;
	}

	@ModelAttribute("rooms")
	public Collection<RoomsType> populateRoomsTypes() {
		return this.hotelService.findRoomsTypes();
	}

	@GetMapping(value = "/owners/*/pets/{petId}/hotel/new")
	public String initNewHotelForm(@PathVariable("petId") int petId, Map<String, Object> model) {

		return VIEWS_HOTEL_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/hotel/new")
	public String processNewHotelForm(@Valid Hotel hotel, @PathVariable("petId") int petId, BindingResult result)
			throws DataAccessException {
		if (result.hasErrors()) {
			return VIEWS_HOTEL_CREATE_OR_UPDATE_FORM;
		} else if (hotel.getDate1().isAfter(hotel.getDate2())) {
			result.rejectValue("date2", "Fecha de salida anterior a la fecha de entrada",
					"Fecha de salida anterior a la fecha de entrada.");

			return VIEWS_HOTEL_CREATE_OR_UPDATE_FORM;
		} else {
			for (Hotel ho : hotelService.findHotelsByPetId(petId)) {
				if (ho.getDate1().equals(hotel.getDate1())) {
					result.rejectValue("room",
							"Ya tienes una reserva existente para ese dia",
							"Ya tienes una reserva existente para ese dia");

					return VIEWS_HOTEL_CREATE_OR_UPDATE_FORM;
				}else if (hotel.getDate1().isBefore(ho.getDate2())) {
					result.rejectValue("room",
							"No puedes hacer una reserva porque su mascota ya esta hospedada en una habitacion este dia",
							"No puedes hacer una reserva porque su mascota ya esta hospedada en una habitacion este dia");

					return VIEWS_HOTEL_CREATE_OR_UPDATE_FORM;
				}
			}

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
