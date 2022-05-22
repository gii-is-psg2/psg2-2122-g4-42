package org.springframework.samples.petclinic.clinicOwner;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicOwnerRepository extends CrudRepository<ClinicOwner, Integer> {

	ClinicOwner findById(ClinicOwnerPlanType id) throws DataAccessException;

	List<ClinicOwner> findAll();

	ClinicOwner findByUser(User user);

}
