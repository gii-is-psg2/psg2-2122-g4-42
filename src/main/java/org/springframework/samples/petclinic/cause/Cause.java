package org.springframework.samples.petclinic.cause;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "causes")
public class Cause extends NamedEntity{
    
    @NotEmpty
    @NotNull
    private String description;

    private double budgetTarget;

    @NotEmpty
    @NotNull
    private String organization;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
    private Set<Donation> donations;

    protected Set<Donation> getDonationsInternal() {
        if (this.donations == null) {
            this.donations = new HashSet<>();
        }
        return this.donations;
    }

    public List<Donation> getDonations(){
        List<Donation> sortedDonations = new ArrayList<>(getDonationsInternal());
        PropertyComparator.sort(sortedDonations, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedDonations);
    }

    public void addDonation(Donation donation) {
        getDonationsInternal().add(donation);
        donation.setCause(this);
    }
    
}
