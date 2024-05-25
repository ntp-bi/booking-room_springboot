package com.Booking.Booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.EventDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.entity.Events;
import com.Booking.Booking.repository.IEventRepository;
import com.Booking.Booking.service.impl.IEventServiceImpl;


@Service
public class EventService implements IEventServiceImpl{
	
	private final int pageSize = 10;

	@Autowired
	IEventRepository eventRepository;
	
	@Override
	public int addEvent(String eventName) {
		try {
			List<Events> getEvent = eventRepository.findAll();
			for (Events e : getEvent){
				System.out.println(e.getEventName());
				if(e.getEventName().equalsIgnoreCase(eventName)){
					return 0 ;
				}
			}
			Events event = new Events();
			event.setEventName(eventName);

			eventRepository.save(event);
			return event.getId();
		} catch (Exception e) {
			System.out.print(e);
			return 0;
		}
	}

	@Override
	public boolean updateEvent(int id, String eventName) {
		boolean isUpdate = false;
		try {
			List<Events> getEvents = eventRepository.findEventName(id);
			for (Events events : getEvents){
				if(eventName.equalsIgnoreCase(events.getEventName())){
					return isUpdate;
				}
			}
			Optional<Events> getEvent = eventRepository.findById(id);
			if (!getEvent.isPresent())
				return isUpdate;

			Events event = getEvent.get();
			event.setEventName(eventName);
			eventRepository.save(event);
			isUpdate = true;
		} catch (Exception e) {
			System.out.print(e);
			return isUpdate;
		}

		return isUpdate;
	}

	@Override
	public boolean deleteEvent(int id) {
		boolean isDelete = false;
		try {
			Optional<Events> event = eventRepository.findById(id);
			if (!event.isPresent())
				return isDelete;
			eventRepository.deleteById(id);
			isDelete = true;
		} catch (Exception e) {
			System.out.print(e);
			return isDelete;
		}

		return isDelete;
	}

	@Override
	public PaginationResponseDTO<EventDTO> eventList(int page, String searchValue) {
		if((searchValue) != "") searchValue="%" + searchValue + "%"; 
		Pageable pagination = PageRequest.of(page, pageSize);
		List<Events> events = eventRepository.search(pagination, searchValue);
		
		List<EventDTO> listEvents= new ArrayList<EventDTO>();
		if(events.size() < 0) return null;
		
		for(Events item : events)
			listEvents.add(new EventDTO(item.getId(),item.getEventName()));
		int rowCount = eventRepository.getRowCount(searchValue);
		int pageCount = rowCount/pageSize;
		
		if (rowCount%pageSize>0) ++pageCount;
		
		
		return new PaginationResponseDTO<EventDTO>(listEvents, rowCount, pageCount);
	}

	@Override
	public List<EventDTO> getAllEvents() {
		List<Events> events = eventRepository.findAll();
		List<EventDTO> listEvents = new ArrayList<EventDTO>();
		
		for (Events i : events) {
			EventDTO event = new EventDTO();
			
			event.setId(i.getId());
			event.setEventName(i.getEventName());
			
			listEvents.add(event);
		}
		
		return listEvents;
	}

	@Override
	public EventDTO getByEventId(int id) {
		Optional<Events> getEvent = eventRepository.findById(id);
		EventDTO result = new EventDTO();
		if(getEvent != null) {
			result.setId(getEvent.get().getId());
			result.setEventName(getEvent.get().getEventName());
		}
		
		return result;
	}

}
