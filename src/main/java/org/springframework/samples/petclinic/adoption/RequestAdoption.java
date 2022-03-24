package org.springframework.samples.petclinic.adoption;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
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

    @NotNull
    private Status status;

    public String toString(){
        return this.description + this.status;
    }
}
