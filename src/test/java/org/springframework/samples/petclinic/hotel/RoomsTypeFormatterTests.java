package org.springframework.samples.petclinic.hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RoomsTypeFormatterTests {
	
	@Mock
	private HotelService hotelService;

	private RoomsTypeFormatter roomTypeFormatter;
	
	@BeforeEach
	void setup() {
		roomTypeFormatter = new RoomsTypeFormatter(hotelService);
	}
	
	@Test
	void testPrint() {
		RoomsType roomType = new RoomsType();
		roomType.setName("Habitacion_7");
		String roomTypeName = roomTypeFormatter.print(roomType, Locale.ENGLISH);
		assertEquals("Habitacion_7", roomTypeName);
	}
	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(hotelService.findRoomsTypes()).thenReturn(makeRoomsTypes());
		Assertions.assertThrows(ParseException.class, () -> {
			roomTypeFormatter.parse("Habitacion_10", Locale.ENGLISH);
		});
	}
	
	@Test
	void shouldParse() throws ParseException {
		Mockito.when(hotelService.findRoomsTypes()).thenReturn(makeRoomsTypes());
		RoomsType roomType = roomTypeFormatter.parse("Habitacion_9", Locale.ENGLISH);
		assertEquals("Habitacion_9", roomType.getName());
	}
	
	private Collection<RoomsType> makeRoomsTypes() {
		Collection<RoomsType> roomTypes = new ArrayList<>();
		roomTypes.add(new RoomsType() {
			{
				setName("Habitacion_8");
			}
		});
		roomTypes.add(new RoomsType() {
			{
				setName("Habitacion_9");
			}
		});
		return roomTypes;
	}

}
