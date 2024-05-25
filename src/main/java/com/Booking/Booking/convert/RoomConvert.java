package com.Booking.Booking.convert;

import org.springframework.stereotype.Component;

import com.Booking.Booking.dto.RoomDTO;
import com.Booking.Booking.entity.RoomTypes;
import com.Booking.Booking.entity.Rooms;


@Component
public class RoomConvert {

	public Rooms toEntity(RoomDTO dto) {
		Rooms entity = new Rooms();
		entity.setId(dto.getId());
		entity.setRoomName(dto.getRoomName());
		entity.setPhoto(dto.getImage());
		entity.setStatus(dto.getStatus());
		entity.setArea(dto.getArea());
		entity.setCountOfSeats(dto.getCountOfSeats());
		entity.setDescription(dto.getDescription());
		RoomTypes roomTypes = new RoomTypes();
		roomTypes.setId(dto.getTypeId());
		roomTypes.setTypeName(dto.getTypeName());
		entity.setRoomType(roomTypes);
		return entity;
	}
	
	
	public RoomDTO toDTO(Rooms entity) {
		RoomDTO dto = new RoomDTO();
		dto.setId(entity.getId());
		dto.setRoomName(entity.getRoomName());
		dto.setImage(entity.getPhoto());
		dto.setStatus(entity.getStatus());
		dto.setArea(entity.getArea());
		dto.setCountOfSeats(entity.getCountOfSeats());
		dto.setDescription(entity.getDescription());
		dto.setTypeId(entity.getRoomType().getId());
		dto.setTypeName(entity.getRoomType().getTypeName());

		return dto;
	}
}
