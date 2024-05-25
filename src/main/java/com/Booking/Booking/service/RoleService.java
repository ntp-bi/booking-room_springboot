package com.Booking.Booking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.RoleDTO;
import com.Booking.Booking.entity.Roles;
import com.Booking.Booking.repository.IRoleRepository;
import com.Booking.Booking.service.impl.IRoleServiceImpl;

@Service
public class RoleService implements IRoleServiceImpl {
	@Autowired
	IRoleRepository rolesRepo;

	@Override
	public List<RoleDTO> list() {
		List<Roles> roles = rolesRepo.findAll();
		List<RoleDTO> result = new ArrayList<>();
		
		for(Roles item : roles)
			result.add(new RoleDTO(item.getId(), item.getRoleName()));
		
		return result;
	}

}