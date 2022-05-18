package org.springframework.samples.petclinic.cause;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "searchs")
public class Search extends BaseEntity {
    
    @NotEmpty
    @Column(name = "search")
    private String search;

}
