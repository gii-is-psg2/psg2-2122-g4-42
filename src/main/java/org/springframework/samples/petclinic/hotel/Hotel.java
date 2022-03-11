package org.springframework.samples.petclinic.hotel;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.pet.Pet;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "hotels")

public class Hotel extends NamedEntity {

	@Column(name = "date1")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	@NotNull
	private LocalDate date1;

	@Column(name = "date2")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	@NotNull
	private LocalDate date2;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@ManyToOne
	@JoinColumn(name = "room_id")
	@NotNull
	private RoomsType room;

	public Hotel() {
		this.date1 = LocalDate.now();
	}

	public RoomsType getRoom() {
		return this.room;
	}

	public LocalDate getDate1() {
		return this.date1;
	}

	public LocalDate getDate2() {
		return this.date2;
	}

	public void setDate1(LocalDate date1) {
		this.date1 = date1;
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

	public void setRoom(RoomsType room) {
		this.room = room;
	}

}
