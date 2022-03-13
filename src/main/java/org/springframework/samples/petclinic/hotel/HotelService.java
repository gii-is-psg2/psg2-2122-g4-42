package org.springframework.samples.petclinic.hotel;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

	private HotelRepository hotelRepository;

	@Autowired
	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Transactional(readOnly = true)
	public Collection<RoomsType> findRoomsTypes() throws DataAccessException {
		return hotelRepository.findRoomsTypes();
	}

	@Transactional
	public void saveHotel(Hotel hotel) throws DataAccessException {
		hotelRepository.save(hotel);
	}

	public Collection<Hotel> findHotelsByPetId(int petId) {
		return hotelRepository.findByPetId(petId);
	}

	@Transactional(readOnly = true)
	public Hotel findHotelById(int id) throws DataAccessException {
		return hotelRepository.findById(id);
	}

}
