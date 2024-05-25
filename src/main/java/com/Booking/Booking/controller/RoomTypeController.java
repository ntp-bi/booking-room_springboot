package com.Booking.Booking.controller;

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
import com.Booking.Booking.dto.RoomTypeDTO;
import com.Booking.Booking.repository.IRoomTypeRepository;
import com.Booking.Booking.service.impl.IRoomTypeServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController extends ControllerBase {

	@Autowired
	IRoomTypeRepository roomTypeRepository;

	@Autowired
	IRoomTypeServiceImpl roomTypeImp;

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	public ResponseEntity<?> getListEvent() {
		ResponseDTO<List<RoomTypeDTO>> result = new ResponseDTO<>();
		try {
			List<RoomTypeDTO> listRoomTypes = roomTypeImp.getAllRoomType();

			result.setData(listRoomTypes);
			result.setMessage(listRoomTypes == null ? "Không tìm thấy loại phòng" : "Đã tìm thấy loại phòng");

			return listRoomTypes == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> getRoomTypeById(@PathVariable String id) {
		ResponseDTO<RoomTypeDTO> result = new ResponseDTO<>();
		try {
			int roomTypeId = Integer.valueOf(id);
			RoomTypeDTO roomType = roomTypeImp.getByRoomTypeId(roomTypeId);

			result.setData(roomType);
			result.setMessage(roomType == null ? "Không tìm thấy loại phòng" : "Đã tìm thấy loại phòng");

			return roomType == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addEvent(@RequestBody RoomTypeDTO roomType) {
		ResponseDTO<Integer> result = new ResponseDTO<>();
		try {
			int id = roomTypeImp.addRoomType(roomType.getTypeName());
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
	public ResponseEntity<?> updateEvent(@PathVariable String id, @RequestBody RoomTypeDTO roomType) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int roomTypeId = Integer.valueOf(id);
			boolean isSuccess = roomTypeImp.updateRoomType(roomTypeId, roomType.getTypeName());
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
	public ResponseEntity<?> deleteEvent(@PathVariable String id) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int roomTypeId = Integer.valueOf(id);
			boolean isSuccess = roomTypeImp.deleteRoomType(roomTypeId);
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
		ResponseDTO<PaginationResponseDTO<RoomTypeDTO>> result = new ResponseDTO<>();
		try {
			int setPage = Integer.valueOf(page);
			PaginationResponseDTO<RoomTypeDTO> listRoomTypes = roomTypeImp.roomTypeList(setPage, searchValue);
			result.setData(listRoomTypes);
			result.setMessage(listRoomTypes.getData().isEmpty() ? "Không tìm thấy loại phòng" : "Đã tìm thấy loại phòng");

			return listRoomTypes.getData().isEmpty()? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}

	}
}
