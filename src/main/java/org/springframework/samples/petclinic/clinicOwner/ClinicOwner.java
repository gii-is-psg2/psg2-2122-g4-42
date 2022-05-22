/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.clinicOwner;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Getter
@Setter
@Table(name = "clinic-owners")
public class ClinicOwner extends NamedEntity {

	@Column(name = "address")
	@NotEmpty
	private String address;

	@Column(name = "city")
	@NotEmpty
	private String city;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	@Enumerated(EnumType.STRING)
	private ClinicOwnerPlanType plan;


	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	public String getAddress() {
		return this.address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public ClinicOwnerPlanType getPlan() {
		return plan;
	}

	public void setPlan(ClinicOwnerPlanType plan) {
		this.plan = plan;
	}


	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("new", this.isNew()).append("name", this.getName())
				.append("address", this.address).append("city", this.city)
				.append("telephone", this.telephone).append("plan", this.plan.toString()).toString();
	}

}
