package com.Booking.Booking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.UserAccountDTO;

@Service
public interface IUserAccountsServiceImpl {
	String login(String userName, String password);
	List<UserAccountDTO> list();
	PaginationResponseDTO<UserAccountDTO> search(int page, String searchValue);
	UserAccountDTO get(int userId);
	int add(UserAccountDTO payload);
	boolean update(int userId, UserAccountDTO payload);
	boolean delete(int userId);
	String changePassword(String userName, String oldPassword, String newPassword, String confirmPassword);


}
