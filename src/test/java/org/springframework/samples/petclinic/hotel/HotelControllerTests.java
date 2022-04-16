package org.springframework.samples.petclinic.hotel;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HotelController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class HotelControllerTests {

	private static final int TEST_PET_ID = 1;

	@MockBean
	private PetService petService;

	@MockBean
	private HotelService hotelService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitNewHotelForm() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotel/new", TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(view().name("pets/createOrUpdateHotelForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessNewHotelFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/*/pets/{petId}/hotel/new", TEST_PET_ID).param("room", "5EStrellas").param("date2","2025-04-16").with(csrf())).andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowHotels() throws Exception {
		mockMvc.perform(get("/owners/*/pets/{petId}/hotels", TEST_PET_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("hotels")).andExpect(view().name("hotelList"));
	}


}
