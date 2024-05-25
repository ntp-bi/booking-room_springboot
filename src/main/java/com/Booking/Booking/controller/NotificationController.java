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

import com.Booking.Booking.dto.NotificationDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.repository.INotificationRepository;
import com.Booking.Booking.service.impl.INotificationServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
public class NotificationController extends ControllerBase {

	@Autowired
	INotificationRepository notificationRepositoy;

	@Autowired
	INotificationServiceImpl notificationServiceImp;

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	public ResponseEntity<?> getListNotification(@RequestParam int id) {
		ResponseDTO<List<NotificationDTO>> result = new ResponseDTO<>();
		try {			
			List<NotificationDTO> listNotifications = notificationServiceImp.getAllNotifications(id);

			result.setData(listNotifications);
			result.setMessage(listNotifications == null ? "Không có thông báo nào" : "Tìm thấy có thông báo");

			return listNotifications == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	public ResponseEntity<?> getEventById(@PathVariable String id) {
		ResponseDTO<NotificationDTO> result = new ResponseDTO<>();
		try {
			int notificationId = Integer.valueOf(id);
			NotificationDTO notification = notificationServiceImp.getByNotificationId(notificationId);
			result.setData(notification);
			result.setMessage(notification == null ? "Không có thông báo nào" : "Tìm thấy có thông báo");

			return notification == null ? notFound(result) : ok(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@PostMapping("/sent")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> sentNotification(@RequestBody NotificationDTO notification) {
		ResponseDTO<Integer> result = new ResponseDTO<>();
		try {
			//Nếu RecieverId = null thì sẽ gữi cho mọi người
			int id = notificationServiceImp.sentNotification(notification.getNotificationTitle(),
					notification.getNotificationContent(), notification.getAuthorId(), notification.getRecieverId());
			result.setData(id);
			result.setMessage(id == 0 ? "Gữi không thành công" : "Gữi thành công");

			return id == 0 ? notFound(result) : created(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> updateNotification(@PathVariable String id, @RequestBody NotificationDTO notification) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int notificationId = Integer.valueOf(id);
			boolean isSuccess = notificationServiceImp.updateNotification(notificationId,
					notification.getNotificationTitle(), notification.getNotificationContent());
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
	public ResponseEntity<?> deleteNotification(@PathVariable String id) {
		ResponseDTO<Boolean> result = new ResponseDTO<>();
		try {
			int notificationId = Integer.valueOf(id);
			boolean isSuccess = notificationServiceImp.deleteNotification(notificationId);
			result.setData(isSuccess);
			result.setMessage(isSuccess == false ? "Xóa không thành công" : "Xóa thành công");

			return isSuccess == false ? notFound(result) : created(result);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return badRequest(result);
		}

	}
}
