package com.Booking.Booking.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.RoleDTO;


@Service
public interface IRoleServiceImpl {
	List<RoleDTO> list();
}
