package org.springframework.samples.petclinic.user;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;





public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
	@Query("SELECT a FROM Authorities a where user.username= :username ")
	public Set<Authorities> findByUser(@Param("username") String username);
	
	void delete(Authorities a) throws DataAccessException;
}
