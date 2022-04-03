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
public class RequestAdoptionServiceTests {
    
    @Autowired
    protected RequestAdoptionService requestAdoptionService;

    @Autowired
    protected PetService petService;

    @Autowired
    protected AdoptionService adoptionService;

    @Test
    void shouldFindRequestAdoptionById() {
        RequestAdoption requestAdoption1 = this.requestAdoptionService.findRequestAdoptionById(1);
        assertThat(requestAdoption1.getDescription()).isEqualTo("Me siento muy solo y necesito compania, prometo cuidarlo y darle mucho amor");
    }

    @Test
    void shouldFindAllRequestAdoptions(){
        List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        assertEquals(requestAdoptions.size(), 3);
    }

    @Test
    @Transactional
    public void shouldInsertRequestAdoption(){
        List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        int found = requestAdoptions.size();

        RequestAdoption requestAdoption = new RequestAdoption();
        requestAdoption.setDescription("Es muy grande para nuestra casa");
        requestAdoption.setStatus(Status.WAITING);
        requestAdoption.setAdoption(this.adoptionService.findAdoptionById(1));
        this.requestAdoptionService.saveRequestAdoption(requestAdoption);

        assertThat(requestAdoption.getId()).isNotNull();
        assertThat(this.requestAdoptionService.findAllRequestAdoptions().size()).isEqualTo(found+1);
    }

    @Test
    public void shouldUpdateRequestAdoptionDescription() throws Exception {
        RequestAdoption requestAdoption1 = this.requestAdoptionService.findRequestAdoptionById(1);
        String oldDesciption = requestAdoption1.getDescription();

        String newDescription = oldDesciption + "...";
        requestAdoption1.setDescription(newDescription);
        this.requestAdoptionService.saveRequestAdoption(requestAdoption1);

        requestAdoption1 = this.requestAdoptionService.findRequestAdoptionById(1);
        assertThat(requestAdoption1.getDescription()).isEqualTo(newDescription);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteRequestAdoption(){
        List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        int found = requestAdoptions.size();

        this.requestAdoptionService.deleteRequestAdoption(1);
        requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        assertEquals(requestAdoptions.size(), found-1);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteRequestAdoptions(){
        List<RequestAdoption> requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        int found = requestAdoptions.size();
        assertNotEquals(found, 0);

        this.requestAdoptionService.deleteAllRequestAdoptions();
        requestAdoptions = this.requestAdoptionService.findAllRequestAdoptions();
        assertEquals(requestAdoptions.size(), 0);
    }
}