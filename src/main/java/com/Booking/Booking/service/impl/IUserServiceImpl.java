package com.Booking.Booking.service.impl;


import java.util.Date;
import java.util.List;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;


public interface IUserServiceImpl {

	int addUser(MultipartFile file, String fullName, Date birthDay, boolean gender);
	boolean updateUser (MultipartFile file, int id, String fullName, Date birthDay, boolean gender);
	boolean deleteUser (int id);
	PaginationResponseDTO<UserDTO> userList(int page, String searchValue);
	List<UserDTO> getAllUser();
	UserDTO getByUserId(int id);
}
