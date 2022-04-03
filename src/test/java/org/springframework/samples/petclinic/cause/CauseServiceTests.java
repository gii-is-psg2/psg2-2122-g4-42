package org.springframework.samples.petclinic.cause;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Modifying;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CauseServiceTests {
    
    @Autowired
    protected CauseService causeService;

    @Test
    void shouldFindCauseById() {
        Cause cause1 = this.causeService.findCauseById(1);
        assertThat(cause1.getName()).isEqualTo("Parvovirosis");
    }

    @Test
    void shouldFindAllCauses(){
        List<Cause> causes = this.causeService.findAllCauses();
        assertEquals(causes.size(), 3);
    }

    @Test
    @Transactional
    public void shouldInsertCause(){
        List<Cause> causes = this.causeService.findAllCauses();
        int found = causes.size();

        Cause cause = new Cause();
        cause.setName("Cause1");
        cause.setDescription("Cause1's description");
        cause.setBudgetTarget(2000);
        cause.setOrganization("Cause1's organization");

        this.causeService.saveCause(cause);

        assertThat(cause.getId()).isNotNull();
        assertThat(this.causeService.findAllCauses().size()).isEqualTo(found+1);
    }

    @Test
    public void shouldUpdateCauseName() throws Exception {
        Cause cause2 = this.causeService.findCauseById(2);
        String oldName = cause2.getName();

        String newName = oldName + "X";
        cause2.setName(newName);
        this.causeService.saveCause(cause2);

        cause2 = this.causeService.findCauseById(2);
        assertThat(cause2.getName()).isEqualTo(newName);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteCause(){
        List<Cause> causes = this.causeService.findAllCauses();
        int found = causes.size();

        this.causeService.deleteCause(3);
        causes = this.causeService.findAllCauses();
        assertEquals(causes.size(), found-1);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteCauses(){
        List<Cause> causes = this.causeService.findAllCauses();
        int found = causes.size();
        assertNotEquals(found, 0);

        this.causeService.deleteAllCauses();
        causes = this.causeService.findAllCauses();
        assertEquals(causes.size(), 0);
    }

    @Test
    @Transactional
    public void shouldInsertDonation(){

        int donationsSize = this.causeService.findDonations().size();
        Donation donation = new Donation();

        donation.setDate(LocalDate.of(2010, 03, 30));
        donation.setClient("Juanan");
        donation.setAmount(40.0);
        donation.setCause(this.causeService.findCauseById(1));
        
        this.causeService.saveDonation(donation);

        assertEquals(this.causeService.findDonations().size(), donationsSize + 1);
    }

    @Test
    void shouldFindAllDonations(){
        Collection<Donation> donations = this.causeService.findDonations();
        assertEquals(donations.size(), 3);
    }

    @Test
    @Transactional
    @Modifying
    void shouldDeleteDonation(){
        Collection<Donation> donations = this.causeService.findDonations();
        int found = donations.size();

        this.causeService.deleteDonation(2);
        donations = this.causeService.findDonations();
        assertEquals(donations.size(), found-1);
    }

    @Test
    void shouldFindDonationsByCauseId() {
        Collection<Donation> donations = this.causeService.findDonationsByCauseId(3);
        assertThat(donations.size()).isEqualTo(3);
    }
}
