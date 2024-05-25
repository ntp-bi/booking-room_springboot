package com.Booking.Booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.RoleDTO;
import com.Booking.Booking.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController extends ControllerBase {
	@Autowired
	RoleService rolesService;

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> list() {
		try {
			List<RoleDTO> result = rolesService.list();
			String message = result.size() > 0 ? "Get list role success" : "Get list role failed";
			ResponseDTO<List<RoleDTO>> response = new ResponseDTO<List<RoleDTO>>(message, result);

			return result.size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			String message = "Get list role error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());

			return badRequest(response);
		}
	}
}