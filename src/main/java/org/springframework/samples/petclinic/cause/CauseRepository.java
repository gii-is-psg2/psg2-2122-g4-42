package org.springframework.samples.petclinic.cause;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface CauseRepository extends Repository<Cause,Integer>{

    void save(Cause cause) throws DataAccessException;
    
    Cause findById(int id) throws DataAccessException;

    List<Cause> findAll();

    @Modifying
    @Query("DELETE Cause cause WHERE cause.id = :id")
    void deleteById(@Param("id") int id);

    void deleteAll();

}
