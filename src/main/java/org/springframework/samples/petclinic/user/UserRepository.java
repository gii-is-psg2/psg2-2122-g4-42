package org.springframework.samples.petclinic.user;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends  CrudRepository<User, String>{
	public User findByUsername(String username) throws DataAccessException;
}
