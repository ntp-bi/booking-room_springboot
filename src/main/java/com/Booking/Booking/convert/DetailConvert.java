package com.Booking.Booking.convert;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Booking.Booking.dto.DetailDTO;
import com.Booking.Booking.entity.Details;
import com.Booking.Booking.entity.Events;
import com.Booking.Booking.entity.Rooms;
import com.Booking.Booking.entity.Users;
import com.Booking.Booking.repository.IDetailRepository;

@Component
public class DetailConvert {

	@Autowired
	IDetailRepository detailRepository;

	@Autowired
	ConvertDate converDate;

	public Details toEntity(DetailDTO dto) {
		Details entity = new Details();
		entity.setId(dto.getDetailId());
		Users user = new Users();
		user.setId(dto.getUserId());
		user.setFullName(dto.getUserName());
		entity.setUser(user);

		Rooms rooms = new Rooms();
		rooms.setId(dto.getRoomId());
		rooms.setRoomName(dto.getRoomName());
		rooms.setPhoto(dto.getRoomPhoto());
		rooms.setStatus(dto.getRoomStatus());
		entity.setRoom(rooms);

		Events events = new Events();
		events.setId(dto.getEventId());
		events.setEventName(dto.getEventName());
		entity.setEvent(events);

		try {
			Date[] formatted = converDate.parseStringtoDate(dto.getReserveTime(), dto.getEndTime(),
					dto.getReturnTime());
			entity.setReserveTime(formatted[0]);
			entity.setEndTime(formatted[1]);
			entity.setReturnTime(formatted[2]);

			if (dto.getAcceptTime() != null) {
				Date acceptTime = converDate.parseStringtoDate(dto.getAcceptTime());
				entity.setAcceptTime(acceptTime);
			}
		} catch (Exception e) {
			System.out.println("Lỗi: " + e.getMessage());
			return null;
		}

		entity.setStatus(dto.getStatus());

		return entity;
	}

	public DetailDTO toDTO(Details entity) {
		DetailDTO dto = new DetailDTO();
		dto.setDetailId(entity.getId());

		Users user = new Users();
		user.setId(entity.getUser().getId());
		user.setFullName(entity.getUser().getFullName());
		dto.setUserId(user.getId());
		dto.setUserName(user.getFullName());

		Rooms rooms = new Rooms();
		rooms.setId(entity.getRoom().getId());
		rooms.setRoomName(entity.getRoom().getRoomName());
		rooms.setPhoto(entity.getRoom().getPhoto());
		rooms.setStatus(entity.getRoom().getStatus());
		dto.setRoomId(rooms.getId());
		dto.setRoomName(rooms.getRoomName());
		dto.setRoomPhoto(rooms.getPhoto());
		dto.setRoomStatus(rooms.getStatus());

		Events events = new Events();
		events.setId(entity.getEvent().getId());
		events.setEventName(entity.getEvent().getEventName());
		dto.setEventId(events.getId());
		dto.setEventName(events.getEventName());

		String[] formatted = converDate.formatDatetoString(entity.getReserveTime(), entity.getEndTime(),
				entity.getReturnTime());

		dto.setReserveTime(formatted[0]);
		dto.setEndTime(formatted[1]);
		dto.setReturnTime(formatted[2]);

		if (dto.getAcceptTime() != null) {
			String accepttime = converDate.formatDatetoString(entity.getAcceptTime());
			dto.setAcceptTime(accepttime);
		}

		dto.setStatus(entity.getStatus());

		return dto;
	}
}
