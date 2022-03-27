package org.springframework.samples.petclinic.adoption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.samples.petclinic.pet.PetService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdoptionServiceTests {
    
    @Autowired
    protected AdoptionService adoptionService;
    @Autowired
    protected PetService petService;

    @Test
    void shouldFindAdoptionById() {
        Adoption adoption1 = this.adoptionService.findAdoptionById(1);
        assertThat(adoption1.getDescription()).isEqualTo("No estamos en casa tanto y pasa solo mucho tiempo");
    }

    @Test
    void shouldFindAllAdoptions(){
        List<Adoption> adoptions = this.adoptionService.findAllAdoptions();
        assertEquals(adoptions.size(), 2);
    }

    @Test
    @Transactional
    public void shouldInsertAdoption(){
        List<Adoption> adoptions = this.adoptionService.findAllAdoptions();
        int found = adoptions.size();

        Adoption adoption = new Adoption();
        adoption.setDescription("Es muy grande para nuestra casa");
        adoption.setPet(petService.findPetById(2));

        this.adoptionService.saveAdoption(adoption);

        assertThat(adoption.getId()).isNotNull();
        assertThat(this.adoptionService.findAllAdoptions().size()).isEqualTo(found+1);
    }

    @Test
    public void shouldUpdateAdoptionDescription() throws Exception {
        Adoption adoption1 = this.adoptionService.findAdoptionById(1);
        String oldDesciption = adoption1.getDescription();

        String newDescription = oldDesciption + "...";
        adoption1.setDescription(newDescription);
        this.adoptionService.saveAdoption(adoption1);

        adoption1 = this.adoptionService.findAdoptionById(1);
        assertThat(adoption1.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteAdoption(){
        List<Adoption> adoptions = this.adoptionService.findAllAdoptions();
        int found = adoptions.size();

        this.adoptionService.deleteAdoption(1);
        adoptions = this.adoptionService.findAllAdoptions();
        assertEquals(adoptions.size(), found-1);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteAdoptions(){
        List<Adoption> adoptions = this.adoptionService.findAllAdoptions();
        int found = adoptions.size();
        assertNotEquals(found, 0);

        this.adoptionService.deleteAllAdoptions();
        adoptions = this.adoptionService.findAllAdoptions();
        assertEquals(adoptions.size(), 0);
    }
}