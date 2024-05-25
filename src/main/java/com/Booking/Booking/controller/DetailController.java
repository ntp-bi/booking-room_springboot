package com.Booking.Booking.controller;

import java.util.List;

import com.Booking.Booking.dto.DetailDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.payload.BookingRoomPayload;
import com.Booking.Booking.security.UserContext;
import com.Booking.Booking.service.impl.IDetailServiceImpl;
import com.Booking.Booking.service.impl.IFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/detail")
public class DetailController extends ControllerBase{

	@Autowired
	IFileServiceImpl fileServiceImpl;

	@Autowired
	IDetailServiceImpl detailServiceImpl;

	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("/both/searchdetail")
//	@GetMapping("/searchdetail")
	public ResponseEntity<?> searchRoomDetail(@RequestParam(name = "typeid", required = false, defaultValue = "") String typeid,
											  @RequestParam(name = "roomname", required =  false, defaultValue = "") String roomname,
											  @RequestParam(name = "username", required = false, defaultValue = "") String username,
											  @RequestParam(name = "bookingtime", required = false, defaultValue = "") String bookingtime,
											  @RequestParam(name = "detailstatus", required = false, defaultValue = "") String detailstatus ,
											  @RequestParam(name = "page", required = false, defaultValue = "0") String page){

		try {
			if(page.isEmpty()) {
				page = "0";
			}
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<DetailDTO> result =
					detailServiceImpl.searchRoomDetail(pageNum, typeid, username, roomname, bookingtime, detailstatus);
			String message = result.getData().size() > 0 ? "Hiển thị" : "Danh sách rỗng";
			ResponseDTO<PaginationResponseDTO<DetailDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			String message = "Lỗi hiển thị";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

//	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
//	@PostMapping("/both/bookingroom")
////	@PostMapping("/bookingroom")
//	public ResponseEntity<?> bookingRoom(@RequestBody BookingRoomPayload payload){
//
//		//ResponseData responseData = new ResponseData();
//		System.out.println("uID: " + payload.getUserid());
//		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
//		Boolean result = detailServiceImpl.bookingRoom(payload.getUserid(), payload.getRoomid(), payload.getReservetime(), payload.getEndtime(), payload.getEventid());
//		try {
//			System.out.println("KQ: " + result);
//			responseDTO.setData(result);
//			String message = result ? "Đặt phòng thành công"
//					: "Phòng này đã có người đặt vào lúc " + payload.getReservetime() + " đến " + payload.getEndtime()
//					+ ". Vui lòng chọn thời gian khác thời gian này hoặc bạn có thể chọn phòng khác.";
//			responseDTO.setMessage(message);
//			return result ? ok(responseDTO) : notFound(responseDTO);
//		} catch (Exception e) {
//			System.out.println("Error BookingRoom: " + e.getMessage());
//			responseDTO.setMessage("Đặt phòng thất bại");
//			responseDTO.setData(false);
//			return badRequest(responseDTO);
//		}
//	}

	//	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@PostMapping("/both/bookingroom")
//	@PostMapping("/bookingroom")
	public ResponseEntity<?> bookingRoom(@RequestParam("roomid") Integer roomid,
										 @RequestParam(name = "userid", required = false, defaultValue = "") String userid,
										 @RequestParam("reservetime") String reservetime,
										 @RequestParam("endtime") String endtime,
										 @RequestParam(name = "eventid", required = false, defaultValue = "1") String eventid){

		//ResponseData responseData = new ResponseData()
		System.out.println("rid: " + roomid + " " + userid + " " + reservetime + " " + endtime + " " + eventid);
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		Boolean result = detailServiceImpl.bookingRoom(userid, roomid, reservetime, endtime, eventid);
		try {
			System.out.println("KQ: " + result);
			responseDTO.setData(result);
			String message = result ? "Đặt phòng thành công"
					: "Phòng này đã có người đặt vào lúc " + reservetime + " đến " + endtime
					+ ". Vui lòng chọn thời gian khác thời gian này hoặc bạn có thể chọn phòng khác.";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error BookingRoom: " + e.getMessage());
			responseDTO.setMessage("Đặt phòng thất bại");
			responseDTO.setData(false);
			return badRequest(responseDTO);
		}
	}


	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("both/getalldetail")
//	@GetMapping("/getalldetail")
	public ResponseEntity<?> getAllDetail(@RequestParam(name = "page", required = false, defaultValue = "0") String page){
		try {
			if(page.isEmpty()) {
				page = "0";
			}
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<DetailDTO> result = detailServiceImpl.getAllDetail(pageNum);
			String message = result.getData().size() > 0 ? "Hiển thị" : "Danh sách rỗng";
			ResponseDTO<PaginationResponseDTO<DetailDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			String message = "Lỗi hiển thị";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("/both/getdetail/{id}")
//	@GetMapping("/getdetail/{id}")
	public ResponseEntity<?> getDetailById(@PathVariable("id") Integer id){
		ResponseDTO<DetailDTO> responseDTO = new ResponseDTO<>();
		try {
			DetailDTO detailDTO = detailServiceImpl.getDetailById(id);
			System.out.println("DTO: " + detailDTO);
			responseDTO.setData(detailDTO);
			String message = detailDTO != null ? "Thành công" : "Thất bại";
			responseDTO.setMessage(message);
			return detailDTO != null ? ok(responseDTO) : notFound(detailDTO);
		} catch (Exception e) {
			System.out.println("Error Get Detail By Id: " + e.getMessage());
			responseDTO.setData(null);
			responseDTO.setMessage("Thất bại");
			return badRequest(responseDTO);
		}
	}

	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("both/getdetailnonaccept")
//	@GetMapping("/getdetailnonaccept")
	public ResponseEntity<?> getDetailNonAccept(@RequestParam(name = "page", required = false, defaultValue = "0") String page){
		try {
			if(page.isEmpty()) {
				page = "0";
			}
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<DetailDTO> result = detailServiceImpl.getDetailNonAccept(pageNum);
			String message = result.getData().size() > 0 ? "Hiển thị" : "Danh sách rỗng";
			ResponseDTO<PaginationResponseDTO<DetailDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			String message = "Lỗi hiển thị";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("admin/acceptbookingroom/{id}")
//	@PutMapping("/acceptbookingroom/{id}")
	public ResponseEntity<?> acceptBookingRoom(@PathVariable("id") Integer id){
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		try {
			Boolean result = detailServiceImpl.acceptBookingRoom(id);
			detailServiceImpl.refuseBookingRoomSameTime(id);
			responseDTO.setData(result);
			String message = result ? "Xác nhận thành công" : "Xác nhận thất bại";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error acceptBooking: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Xác nhận thất bại");
			return badRequest(responseDTO);
		}
	}

	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("/admin/refusebookingroom/{id}")
//	@PutMapping("/refusebookingroom/{id}")
	public ResponseEntity<?> refuseBookingRoom(@PathVariable("id") Integer id){
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		try {
			Boolean result = detailServiceImpl.refuseBookingRoom(id);
			responseDTO.setData(result);
			String message = result ? "Từ chối thành công" : "Từ chối thất bại";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error refuseBooking: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Từ chối thất bại");
			return badRequest(responseDTO);
		}
	}

	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@PutMapping("both/cancelbookingroom/{id}")
//	@PutMapping("/cancelbookingroom/{id}")
	public ResponseEntity<?> cancelBookingRoom(@PathVariable("id") Integer id){
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		try {
			Boolean result = detailServiceImpl.cancelBookingRoom(id);
			responseDTO.setData(result);
			String message = result ? "Hủy thành công" : "Hủy thất bại";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error cancelBooking: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Hủy thất bại");
			return badRequest(responseDTO);
		}
	}

	//	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("/teacher/returnroom/{id}")
//	@PutMapping("/checkresetroom/{id}")
	public ResponseEntity<?> checkResetRoom(@PathVariable("id") Integer id){
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		try {
			Boolean result = detailServiceImpl.checkResetRoom(String.valueOf(id));
			responseDTO.setData(result);
			String mesage = result ? "Trả phòng thành công" : "Trả phòng thất bại";
			responseDTO.setMessage(mesage);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error checkreset: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Trả phòng thất bại");
			return badRequest(responseDTO);
		}
	}


	@PreAuthorize("hasAuthority('teacher')")
	@GetMapping("/teacher/calendarbooking")
//	@GetMapping("/calendarbooking/{userid}")
	public ResponseEntity<?> CalendarBooking(){
		ResponseDTO<List<DetailDTO>> responseDTO = new ResponseDTO<>();
		try {
			int userid = (int) UserContext.getUserId();
			List<DetailDTO> result = detailServiceImpl.calendarbooking(userid);
			responseDTO.setData(result);
			String message = !result.isEmpty() ? "Lịch trình" : "Không có lịch trình";
			responseDTO.setMessage(message);
			return !result.isEmpty() ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			responseDTO.setData(null);
			responseDTO.setMessage("Không có lịch trình");
			return badRequest(responseDTO);
		}
	}

	@PreAuthorize("hasAuthority('teacher')")
	@PutMapping("/teacher/updatebookingroomwhenwaitingaccept")
//	@PutMapping("/updatebookingroomwhenwaitingaccept")
	public ResponseEntity<?> UpdateBookingRoomWhenWaitingAccept(@RequestParam("id") String id,
																@RequestParam(name = "roomid" , required = false) String roomid,
																@RequestParam(name = "eventid", required = false) String eventid,
																@RequestParam(name = "reservetime", required = false) String reservetime,
																@RequestParam(name = "endtime", required = false) String endtime){

		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		try {
			Boolean result = detailServiceImpl.updateBookingRoomWhenWatingAccept
					(Integer.parseInt(id), Integer.parseInt(roomid),
							Integer.parseInt(eventid), reservetime, endtime);
			responseDTO.setData(result);
			String message = result ? "Chỉnh sửa thành công" : "Chỉnh sửa thất bại";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			responseDTO.setData(false);
			responseDTO.setMessage("Chỉnh sửa thất bại");
			return badRequest(responseDTO);
		}

	}


}
