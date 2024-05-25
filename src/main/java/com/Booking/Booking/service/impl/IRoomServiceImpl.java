package com.Booking.Booking.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.RoomDTO;

public interface IRoomServiceImpl {
	boolean insertRoom(MultipartFile file, String roomname, Double area, Integer status,
					   String description, Integer countofseats, Integer typeid);

	boolean editRoom(Integer roomid, MultipartFile file, String roomname, Double area, Integer status,
					 String description, Integer countofseats, Integer typeid);

	boolean deleteRoom(Integer roomid);


	int totalRoom();

	RoomDTO getRoomById(Integer id);

	PaginationResponseDTO<RoomDTO> searchRoom(Integer page, String status, String typeid,
											  String countofseats, String roomname);

	PaginationResponseDTO<RoomDTO> getAllRoom(Integer page);
}
