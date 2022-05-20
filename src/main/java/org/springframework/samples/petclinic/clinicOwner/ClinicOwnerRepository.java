package org.springframework.samples.petclinic.clinicOwner;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicOwnerRepository extends CrudRepository<ClinicOwner, Integer> {

}
