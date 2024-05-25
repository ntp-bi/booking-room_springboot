package com.Booking.Booking.controller;

import java.util.List;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.UserAccountDTO;
import com.Booking.Booking.payload.AccountLoginPayload;
import com.Booking.Booking.payload.ChangePasswordPayload;
import com.Booking.Booking.service.UserAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin("*")
@RestController
@RequestMapping("/account")
public class AccountController extends ControllerBase {
	@Autowired
	UserAccountsService userAccountService;
	
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> list() {
		try {
			List<UserAccountDTO> result = userAccountService.list();
			String message = result.size() > 0 ? "Get list account success": "Get list account failed";
			ResponseDTO<List<UserAccountDTO>> response = new ResponseDTO<List<UserAccountDTO>>(message, result);
			
			return result.size() > 0 ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Get list account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AccountLoginPayload payload) {
//		System.out.println("LOGIN CONTROLLER : USERNAME = " + payload.getUserName() + " - PASSWORD = " + payload.getPassword());
		try {

			String token = userAccountService.login(payload.getUsername(), payload.getPassword());
			String message = !token.equals("") ? "Login success" : "Login failed";
			ResponseDTO<String> response = new ResponseDTO<String>(message, token);

			return !token.equals("") ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Login error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('admin')")
//	@PreAuthorize("hasROLE('ROLE_ADMIN')")
	// ...?page=...&searchValue=...
	public ResponseEntity<?> search(@RequestParam(name = "page", required = false, defaultValue = "0") String page,
			@RequestParam(name = "searchValue", required = false, defaultValue = "") String searchValue) {
		try {
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<UserAccountDTO> result = userAccountService.search(pageNum, searchValue);
			String message = result.getData().size() > 0 ? "Search account success" : "Search account failed";
			ResponseDTO<PaginationResponseDTO<UserAccountDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Search account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@GetMapping("details/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> details(@PathVariable(name = "id") String id) {
		try {
			int accountId = Integer.valueOf(id);
			UserAccountDTO result = userAccountService.get(accountId);
			String message = result != null ? "Get account detail success" : "Get account detail failed";
			ResponseDTO<UserAccountDTO> response = new ResponseDTO<UserAccountDTO>(message, result);

			return result != null ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Get details account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> add(@RequestBody UserAccountDTO payload) {
		try {
			int result = userAccountService.add(payload);
//			System.out.println(result);
			String message = result > 0 ? "Add account success" : "Add account error";
			ResponseDTO<Integer> response = new ResponseDTO<>(message, result);

			return result > 0 ? created(response) : notFound(response);
		} catch (Exception e) {
			String message = "Add account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> delete(@PathVariable String id) {
		try {
			int accountId = Integer.valueOf(id);
			boolean result = userAccountService.delete(accountId);
			String message = result ? "Delete account success" : "Delete account failed";
			ResponseDTO<Boolean> response = new ResponseDTO<Boolean>(message, result);

			return result ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Delete account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> update(@PathVariable(name = "id") String id, @RequestBody UserAccountDTO payload) {
		try {
//			System.out.println("id = " + id);
			int accountId = Integer.valueOf(id);
			boolean result = userAccountService.update(accountId, payload);
			String message = result ? "Update account success" : "Update account failed";
			ResponseDTO<Boolean> response = new ResponseDTO<Boolean>(message, result);

			return result ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Update account error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PostMapping("/changePassword")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordPayload payload) {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			String result = userAccountService.changePassword(userName, payload.getOldPassword(),
					payload.getNewPassword(), payload.getConfirmPassword());
			String message = result.equals("") ? "Change password success" : "Change password error";
			ResponseDTO<String> response = new ResponseDTO<>(message, result);

			return result.equals("") ? ok(response) : notFound(response);
		} catch (Exception e) {
			String message = "Change password error";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}
}

