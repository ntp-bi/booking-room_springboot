package com.Booking.Booking.service.impl;

import java.util.List;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.RoomTypeDTO;

public interface IRoomTypeServiceImpl {
	
	int addRoomType(String roomTypeName);
	boolean updateRoomType (int id, String roomTypeName);
	boolean deleteRoomType (int id);
	PaginationResponseDTO<RoomTypeDTO> roomTypeList(int page, String searchValue);
	List<RoomTypeDTO> getAllRoomType();
	RoomTypeDTO getByRoomTypeId(int id);
}
