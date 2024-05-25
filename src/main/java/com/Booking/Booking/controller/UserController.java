package com.Booking.Booking.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.UserDTO;
import com.Booking.Booking.repository.IUserRepository;
import com.Booking.Booking.service.impl.IUserServiceImpl;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController extends ControllerBase {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IUserServiceImpl userServiceImp;

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> getListUser() {
		ResponseDTO<List<UserDTO>> result = new ResponseDTO<>();
		try {
			List<UserDTO> listUsers = userServiceImp.getAllUser();

			result.setData(listUsers);
			result.setMessage(listUsers == null ? "Không tìm thấy người dùng" : "Đã tìm thấy người dùng");

			return listUsers == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> getEventById(@PathVariable String id) {
		ResponseDTO<UserDTO> result = new ResponseDTO<>();
		try {
			int userId = Integer.valueOf(id);
			UserDTO user = userServiceImp.getByUserId(userId);
			result.setData(user);
			result.setMessage(user == null ? "Không tìm thấy người dùng" : "Đã tìm thấy người dùng");

			return user == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addUser(@RequestParam(name = "file",required = false) MultipartFile file, @RequestParam String fullName, @RequestParam String birthDay, @RequestParam boolean gender) {
		ResponseDTO<Integer> result = new ResponseDTO<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int id = userServiceImp.addUser(file, fullName, dateFormat.parse(birthDay), gender);
			result.setData(id);
			result.setMessage(id == 0 ? "Thêm không thành công" : "Thêm thành công");

			return id == 0 ? notFound(result) : created(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestParam(name = "file",required = false) MultipartFile file,
										@RequestParam String fullName, @RequestParam String birthDay, @RequestParam boolean gender) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println("file: " + file);
			int userId = Integer.valueOf(id);
			boolean isSuccess = userServiceImp.updateUser(file, userId, fullName, dateFormat.parse(birthDay),
					gender);
			result.setData(isSuccess);
			result.setMessage(isSuccess == false ? "Cập nhật không thành công" : "Cập nhật thành công");

			return isSuccess == false ? notFound(result) : created(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int userId = Integer.valueOf(id);
			boolean isSuccess = userServiceImp.deleteUser(userId);
			result.setData(isSuccess);
			result.setMessage(isSuccess == false ? "Xóa không thành công" : "Xóa thành công");

			return isSuccess == false ? notFound(result) : created(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> list(@RequestParam(name = "page", required = false, defaultValue = "0") String page,
								  @RequestParam(name = "searchValue", required = false, defaultValue = "") String searchValue) {
		ResponseDTO<PaginationResponseDTO<UserDTO>> result = new ResponseDTO<>();
		try {
			int setPage = Integer.valueOf(page);
			PaginationResponseDTO<UserDTO>listUsers = userServiceImp.userList(setPage, searchValue);

			result.setData(listUsers);
			result.setMessage(listUsers.getData().isEmpty() ? "Không tìm thấy người dùng" : "Đã tìm thấy người dùng");

			return listUsers.getData().isEmpty() ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}
}
