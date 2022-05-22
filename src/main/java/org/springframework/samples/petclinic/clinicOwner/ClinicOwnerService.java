/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.samples.petclinic.clinicOwner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicOwnerService {

	private ClinicOwnerRepository clinicOwnerRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ClinicOwnerService(ClinicOwnerRepository clinicOwnerRepository) {
		this.clinicOwnerRepository = clinicOwnerRepository;
	}

	@Transactional(readOnly = true)
	public List<ClinicOwner> findAllClinicOwners() {
		return clinicOwnerRepository.findAll();
	}

	@Transactional
	public void saveClinicOwner(ClinicOwner clinicOwner) throws DataAccessException {
		clinicOwner.setPlan(ClinicOwnerPlanType.BASICO);
		clinicOwnerRepository.save(clinicOwner);
		Set<Authorities> a = new HashSet<>();
		clinicOwner.getUser().setAuthorities(a);
		Set<Authorities> conj = authoritiesService.getAuthoritiesByUSer(clinicOwner.getUser());
		for (Authorities authorities : conj) {
			this.authoritiesService.deleteAuthoritie(authorities);
		}
		userService.saveUser(clinicOwner.getUser());
		authoritiesService.saveAuthorities(clinicOwner.getUser().getUsername(), "basic");

	}

	@Transactional
	public void updatePlanBasico(ClinicOwner clinicOwner) throws DataAccessException {
		clinicOwner.setPlan(ClinicOwnerPlanType.BASICO);
		clinicOwnerRepository.save(clinicOwner);
		Set<Authorities> a = new HashSet<>();
		clinicOwner.getUser().setAuthorities(a);
		Set<Authorities> conj = authoritiesService.getAuthoritiesByUSer(clinicOwner.getUser());
		for (Authorities authorities : conj) {
			this.authoritiesService.deleteAuthoritie(authorities);
		}
		userService.saveUser(clinicOwner.getUser());
		authoritiesService.saveAuthorities(clinicOwner.getUser().getUsername(), "basic");

	}

	@Transactional
	public void updatePlanAdvanced(ClinicOwner clinicOwner) throws DataAccessException {
		clinicOwner.setPlan(ClinicOwnerPlanType.AVANZADO);
		clinicOwnerRepository.save(clinicOwner);
		Set<Authorities> a = new HashSet<>();
		clinicOwner.getUser().setAuthorities(a);
		Set<Authorities> conj = authoritiesService.getAuthoritiesByUSer(clinicOwner.getUser());
		for (Authorities authorities : conj) {
			this.authoritiesService.deleteAuthoritie(authorities);
		}

		userService.saveUser(clinicOwner.getUser());
		authoritiesService.saveAuthorities(clinicOwner.getUser().getUsername(), "advanced");

	}

	@Transactional
	public void updatePlanProfesional(ClinicOwner clinicOwner) throws DataAccessException {
		clinicOwner.setPlan(ClinicOwnerPlanType.PROFESIONAL);
		clinicOwnerRepository.save(clinicOwner);
		Set<Authorities> a = new HashSet<>();
		clinicOwner.getUser().setAuthorities(a);
		Set<Authorities> conj = authoritiesService.getAuthoritiesByUSer(clinicOwner.getUser());
		for (Authorities authorities : conj) {
			this.authoritiesService.deleteAuthoritie(authorities);
		}

		userService.saveUser(clinicOwner.getUser());
		authoritiesService.saveAuthorities(clinicOwner.getUser().getUsername(), "pro");
	}

	@Transactional(readOnly = true)
	public ClinicOwner findByUsername(String username) throws DataAccessException {
		User user = userService.findByUsername(username);
		return clinicOwnerRepository.findByUser(user);
	}

}