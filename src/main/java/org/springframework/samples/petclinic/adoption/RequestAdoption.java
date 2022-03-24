package org.springframework.samples.petclinic.adoption;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "requestAdoptions")
public class RequestAdoption extends BaseEntity{
    @NotEmpty
    private String description;

    @OneToOne
    @JoinColumn(name = "adoption_id")
    private Adoption adoption;

    @NotNull
    private Status status;
}
