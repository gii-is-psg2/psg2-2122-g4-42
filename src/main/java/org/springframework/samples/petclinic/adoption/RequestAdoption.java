package org.springframework.samples.petclinic.adoption;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "requestAdoptions")
public class RequestAdoption extends BaseEntity{
    @NotEmpty
    private String description;

    @NotNull
    private Status status;

    @ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;

    public String toString(){
        return this.description + this.status;
    }
}
