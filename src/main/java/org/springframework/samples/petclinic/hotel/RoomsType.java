package org.springframework.samples.petclinic.hotel;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;

@Entity
@Table(name = "rooms")
public class RoomsType extends NamedEntity{

}
