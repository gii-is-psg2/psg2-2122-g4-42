package org.springframework.samples.petclinic.hotel;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


public interface HotelRepository extends Repository<Hotel, Integer> {

	void save(Hotel hotel) throws DataAccessException;

	List<Hotel> findByPetId(Integer petId);
	
	@Query("SELECT rtype FROM RoomsType rtype ORDER BY rtype.name")
	List<RoomsType> findRoomsTypes() throws DataAccessException;
	
	Hotel findById(int id) throws DataAccessException;

}
