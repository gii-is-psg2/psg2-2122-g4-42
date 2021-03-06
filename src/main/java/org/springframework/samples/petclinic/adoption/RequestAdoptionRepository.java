package org.springframework.samples.petclinic.adoption;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface RequestAdoptionRepository extends Repository<RequestAdoption,Integer>{
    void save(RequestAdoption requestAdoption) throws DataAccessException;
    
    RequestAdoption findById(int id) throws DataAccessException;

    List<RequestAdoption> findAll();

    @Modifying
    @Query("DELETE RequestAdoption ra WHERE ra.id = :id")
    void deleteById(@Param("id") int id);

    void deleteAll();
    
}