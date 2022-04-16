package org.springframework.samples.petclinic.cause;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CauseController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CauseControllerTests {

	private static final int TEST_CAUSE_ID = 1;

	@Autowired
	private CauseController causeController;

	@MockBean
	private CauseService causeService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		given(this.causeService.findCauseById(TEST_CAUSE_ID)).willReturn(new Cause());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitDetailCausePage() throws Exception {
		mockMvc.perform(get("/causes/detail/{causeId}", TEST_CAUSE_ID)).andExpect(status().isOk())
				.andExpect(view().name("mav"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowCause() throws Exception {
		mockMvc.perform(get("/causes/detail/{causeId}", TEST_CAUSE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("cause"));
	}


}
