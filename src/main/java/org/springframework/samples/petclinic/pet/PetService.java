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
package org.springframework.samples.petclinic.pet;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.adoption.Adoption;
import org.springframework.samples.petclinic.adoption.AdoptionRepository;
import org.springframework.samples.petclinic.adoption.RequestAdoptionRepository;
import org.springframework.samples.petclinic.hotel.Hotel;
import org.springframework.samples.petclinic.hotel.HotelRepository;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;

	private VisitRepository visitRepository;

	private HotelRepository hotelRepository;

	private AdoptionRepository adoptionRepository;

	private RequestAdoptionRepository requestAdoptionRepository;

	// private NotificationRepository notificationRepository;

	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository, HotelRepository hotelRepository, AdoptionRepository adoptionRepository,
			RequestAdoptionRepository requestAdoptionRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.hotelRepository = hotelRepository;
		this.adoptionRepository = adoptionRepository;
		this.requestAdoptionRepository = requestAdoptionRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Collection<Visit> findVisits() throws DataAccessException {
		return visitRepository.findAll();
	}

	@Transactional
	public void deleteVisit(int id) throws DataAccessException {
		visitRepository.deleteById(id);
	}

	@Transactional
	public void deleteAllVisits() {
		petRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Pet> findAllPets() {
		return petRepository.findAll();
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
		if (pet.getOwner() != null) {
			Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
			if (StringUtils.hasLength(pet.getName()) && (otherPet != null && otherPet.getId() != pet.getId())) {
				throw new DuplicatedPetNameException();
			} else
				petRepository.save(pet);
		} else
			petRepository.save(pet);
	}

	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	@Transactional
	public void deletePet(int id) throws DataAccessException {
		// we delete the visits and the hotels before the pet due to the restrictions
		// violations
		List<Visit> visits = visitRepository.findByPetId(id);
		for (Visit visit : visits) {
			visitRepository.deleteById(visit.getId());
		}

		List<Hotel> hotels = hotelRepository.findByPetId(id);
		for (Hotel hotel : hotels) {
			hotelRepository.deleteById(hotel.getId());
		}

		Adoption adoption = adoptionRepository.findByPetId(id);
		adoption.getRequestAdoptions().stream().forEach(a -> this.requestAdoptionRepository.deleteById(a.getId()));
		adoptionRepository.deleteById(adoption.getId());
		petRepository.deleteById(id);
	}

	@Transactional
	public void deleteAll() {
		petRepository.deleteAll();
	}

	// @Transactional
	// public void saveNotification(Notification notification) throws DataAccessException {
	// 	notificationRepository.save(notification);
	// }

}
