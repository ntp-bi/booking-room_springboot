package com.Booking.Booking.service.impl;

import java.util.List;

import com.Booking.Booking.dto.EventDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;

public interface IEventServiceImpl {

	int addEvent(String eventName);
	boolean updateEvent (int id, String eventName);
	boolean deleteEvent (int id);
	PaginationResponseDTO<EventDTO> eventList(int page, String searchValue);
	List<EventDTO> getAllEvents();
	EventDTO getByEventId(int id);
}