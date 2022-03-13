package org.springframework.samples.petclinic.hotel;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;


public interface HotelRepository extends Repository<Hotel, Integer> {

	void save(Hotel hotel) throws DataAccessException;

	List<Hotel> findByPetId(Integer petId);
	
	@Query("SELECT rtype FROM RoomsType rtype ORDER BY rtype.name")
	List<RoomsType> findRoomsTypes() throws DataAccessException;
	
	Hotel findById(int id) throws DataAccessException;

	/**
	 * Find all <code>Hotels</code> from the data store.
	 */
	List<Hotel> findAll();

	/**
	 * Delete a <code>Hotel</code> from the data store.
	 * @param id the id to search for
	 */
	@Modifying
	@Query("delete Hotel h where h.id = :id")
	void deleteById(@Param("id") int id);

}
