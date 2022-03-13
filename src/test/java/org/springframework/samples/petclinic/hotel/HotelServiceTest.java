package org.springframework.samples.petclinic.hotel;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class HotelServiceTest {

	@Autowired
	protected HotelService hotelService;

	@Test
	void shouldFindHotelByd() {
		Hotel hotel1 = this.hotelService.findHotelById(1);
		Hotel hotel2 = this.hotelService.findHotelById(3);
		assertThat(hotel1.getName()).startsWith("5EStrellas");
		assertThat(hotel2.getName()).startsWith("NH");

	}

	@Test
	void shouldFindRoomsTypes() {
		Collection<RoomsType> roomsTypes = this.hotelService.findRoomsTypes();

		RoomsType roomsType1 = EntityUtils.getById(roomsTypes, RoomsType.class, 1);
		assertThat(roomsType1.getName()).isEqualTo("Habitacion_1");
		RoomsType roomsType4 = EntityUtils.getById(roomsTypes, RoomsType.class, 4);
		assertThat(roomsType4.getName()).isEqualTo("Habitacion_4");
		RoomsType roomsType3 = EntityUtils.getById(roomsTypes, RoomsType.class, 3);
		assertThat(roomsType3.getName()).isEqualTo("Habitacion_3");
	}

	@Test
	void shouldFindHotelsByPetId() throws Exception {
		Collection<Hotel> hotels1 = this.hotelService.findHotelsByPetId(7);
		Collection<Hotel> hotels2 = this.hotelService.findHotelsByPetId(10);
		assertThat(hotels1.size()).isEqualTo(2);
		assertThat(hotels2.size()).isEqualTo(1);

	}
	


}
