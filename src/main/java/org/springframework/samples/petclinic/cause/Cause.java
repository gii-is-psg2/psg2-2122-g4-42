package org.springframework.samples.petclinic.cause;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("0.0")
    private double moneyRaised;

    @NotEmpty
    @NotNull
    private String organization;
    
}
