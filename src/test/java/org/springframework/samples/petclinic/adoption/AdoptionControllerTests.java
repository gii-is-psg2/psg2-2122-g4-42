package org.springframework.samples.petclinic.adoption;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AdoptionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdoptionControllerTests {

    private static final int TEST_ADOPTION_ID = 1;

    private static final int TEST_REQUEST_ADOPTION_ID = 1;

    private static final int TEST_PET_ID = 1;

    private static final int TEST_OWNER_ID = 1;

    @MockBean
    private AdoptionService adoptionService;

    @MockBean
    private RequestAdoptionService requestAdoptionService;

    @MockBean
    private PetService petService;

    @MockBean
    private OwnerService ownerService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private Adoption adoption;
    private Pet pet;
    private Owner owner;
    private RequestAdoption requestAdoption;

    @BeforeEach
    void setup() {
        pet = new Pet();
        pet.setId(TEST_PET_ID);
        pet.setName("Bipo");

        adoption = new Adoption();
        adoption.setId(TEST_ADOPTION_ID);
        adoption.setPet(pet);
        adoption.setDescription("prueba");

        owner = new Owner();
        owner.setId(TEST_OWNER_ID);
        owner.setFirstName("George");
        owner.setLastName("Franklin");
        owner.setAddress("110 W. Liberty St.");
        owner.setCity("Madison");
        owner.setTelephone("6085551023");

        requestAdoption = new RequestAdoption();
        requestAdoption.setId(TEST_REQUEST_ADOPTION_ID);
        requestAdoption.setAdoption(adoption);
        requestAdoption.setDescription("prueba");
        requestAdoption.setOwner(owner);

        given(this.adoptionService.findAdoptionById(TEST_ADOPTION_ID)).willReturn(adoption);
        given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);
        given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(owner);
        given(this.requestAdoptionService.findRequestAdoptionById(TEST_REQUEST_ADOPTION_ID))
                .willReturn(requestAdoption);
    }

    @WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/adoptions/new"))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void testCreateAdoptionForm() throws Exception {
        mockMvc.perform(post("/adoptions/new")
                .param("description", "descripcion prueba").with(csrf()))
                .andExpect(status().isOk());
        ;
    }

    @WithMockUser(value = "spring")
    @Test
    void testInitCreationFormRequestAdoption() throws Exception {
        mockMvc.perform(get("/adoptions/{adoptionId}/request", TEST_ADOPTION_ID))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void testCreateRequestForm() throws Exception {
        mockMvc.perform(
                post("/adoptions/{adoptionId}/request", TEST_ADOPTION_ID)
                        .param("description", "descripcion prueba").with(csrf()))
                .andExpect(status().isOk());
    }

}
