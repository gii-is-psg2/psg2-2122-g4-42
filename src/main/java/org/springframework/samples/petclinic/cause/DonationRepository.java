package org.springframework.samples.petclinic.cause;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends Repository<Donation, Integer> {

    void save(Donation donation) throws DataAccessException;

    List<Donation> findByCauseId(Integer causeId) throws DataAccessException;

    List<Donation> findAll();

    @Modifying
    @Query("DELETE Donation donation WHERE donation.id = :id")
    void deleteById(@Param("id") int id);

    void deleteAll();
    
}
