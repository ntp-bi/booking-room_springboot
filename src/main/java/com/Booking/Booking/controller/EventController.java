package com.Booking.Booking.controller;

import java.util.List;

import com.Booking.Booking.dto.EventDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.repository.IEventRepository;
import com.Booking.Booking.service.impl.IEventServiceImpl;
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

import com.Booking.Booking.dto.EventDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.repository.IEventRepository;
import com.Booking.Booking.service.impl.IEventServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/events")
public class EventController extends ControllerBase {

	@Autowired
	IEventRepository eventRepository;

	@Autowired
	IEventServiceImpl eventServiceImp;

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	public ResponseEntity<?> getListEvent() {
		ResponseDTO<List<EventDTO>> result = new ResponseDTO<>();
		try {
			List<EventDTO> listEvents = eventServiceImp.getAllEvents();

			result.setData(listEvents);
			result.setMessage(listEvents == null ? "Không có sự kiện" : "Đã tìm thấy sự kiện");

			return listEvents == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> getEventById(@PathVariable String id) {
		ResponseDTO<EventDTO> result = new ResponseDTO<>();
		try {
			int eventId = Integer.valueOf(id);
			EventDTO event = eventServiceImp.getByEventId(eventId);

			result.setMessage(event == null ? "Không có sự kiện" : "Đã tìm thấy sự kiện");
			result.setData(event);

			return event == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}

	}

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> addEvent(@RequestBody EventDTO event) {
		ResponseDTO<Integer> result = new ResponseDTO<>();
		try {
			int id = eventServiceImp.addEvent(event.getEventName());
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
	public ResponseEntity<?> updateEvent(@PathVariable String id, @RequestBody EventDTO event) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int eventId = Integer.valueOf(id);
			boolean isSuccess = eventServiceImp.updateEvent(eventId, event.getEventName());
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
			int eventId = Integer.valueOf(id);
			boolean isSuccess = eventServiceImp.deleteEvent(eventId);
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
		ResponseDTO<PaginationResponseDTO<EventDTO>> result = new ResponseDTO<>();
		try {
			int setPage = Integer.valueOf(page);
			PaginationResponseDTO<EventDTO> listEvent = eventServiceImp.eventList(setPage, searchValue);

			result.setData(listEvent);
			result.setMessage(listEvent.getData().isEmpty() ? "Không có sự kiện" : "Đã tìm thấy sự kiện");

			return listEvent.getData().isEmpty() ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}
}
