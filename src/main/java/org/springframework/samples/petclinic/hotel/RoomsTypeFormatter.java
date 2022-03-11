package org.springframework.samples.petclinic.hotel;


import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;


@Component
public class RoomsTypeFormatter implements Formatter<RoomsType> {

	private final HotelService hotelService;

	@Autowired
	public RoomsTypeFormatter(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	@Override
	public String print(RoomsType roomsType, Locale locale) {
		return roomsType.getName();
	}

	@Override
	public RoomsType parse(String text, Locale locale) throws ParseException {
		Collection<RoomsType> findRoomsTypes = this.hotelService.findRoomsTypes();
		for (RoomsType room : findRoomsTypes) {
			if (room.getName().equals(text)) {
				return room;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}