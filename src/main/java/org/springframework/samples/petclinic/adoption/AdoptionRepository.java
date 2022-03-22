package org.springframework.samples.petclinic.adoption;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface AdoptionRepository extends Repository<Adoption,Integer>{
    void save(Adoption adoption) throws DataAccessException;
    
    Adoption findById(int id) throws DataAccessException;

    List<Adoption> findAll();

    @Modifying
    @Query("DELETE Adoption a WHERE a.id = :id")
    void deleteById(@Param("id") int id);

    void deleteAll();
    
}
