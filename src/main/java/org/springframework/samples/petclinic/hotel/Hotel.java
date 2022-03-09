package org.springframework.samples.petclinic.hotel;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.Pet;

@Entity
@Table(name = "hotels")

public class Hotel extends BaseEntity {

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	
	@Column(name = "date2")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date2;


	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@NotEmpty
	@Column(name = "room")
	private String room;

	public Hotel() {
		this.date = LocalDate.now();
	}

	public String getRoom() {
		return this.room;
	}

	public LocalDate getDate() {
		return this.date;
	}
	
	public LocalDate getDate2() {
		return this.date2;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setDate2(LocalDate date2) {
		this.date2 = date2;
	}


	public Pet getPet() {
		return this.pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;

	}

	public void setRoom(String room) {
		this.room = room;
	}

}
