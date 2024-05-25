package com.Booking.Booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.RoomDTO;
import com.Booking.Booking.service.impl.IFileServiceImpl;
import com.Booking.Booking.service.impl.IRoomServiceImpl;



@CrossOrigin("*")
@RestController
@RequestMapping("/room")
public class RoomController extends ControllerBase{

	@Autowired
	IFileServiceImpl fileServiceImpl;

	@Autowired
	IRoomServiceImpl roomServiceImlp;

	//	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/admin/addnewroom")
//	@PostMapping("/addnewroom")
	public ResponseEntity<?> AddNewRoom(@RequestParam(name = "file",required = false) MultipartFile file,
										@RequestParam String roomname, @RequestParam double area,
										@RequestParam int status, @RequestParam String description,
										@RequestParam int countofseats, @RequestParam int typeid) {

		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		Boolean isSuccess = roomServiceImlp.insertRoom(file, roomname, area,
				status, description, countofseats, typeid);
		try {
			responseDTO.setData(isSuccess);
			String message = isSuccess ? "Thêm phòng thành công" : "Thêm phòng thất bại";
			responseDTO.setMessage(message);
			return isSuccess ? ok(responseDTO) : notFound(responseDTO);

		} catch (Exception e) {
			System.out.println("Lỗi: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Thêm phòng thất bại");
			return badRequest(responseDTO);
		}
	}

	//	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("/admin/editroom/{id}")
//	@PutMapping("/editroom/{id}")
	public ResponseEntity<?> EditRoom(@RequestParam(name = "file",required = false) MultipartFile file,
									  @RequestParam String roomname, @RequestParam double area,
									  @RequestParam int status, @RequestParam String description,
									  @RequestParam int countofseats, @RequestParam int typeid, @PathVariable("id") int id){
		//ResponseData responseData = new ResponseData();
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		Boolean result = roomServiceImlp.editRoom(id, file, roomname, area, status, description, countofseats, typeid);
		try {
			responseDTO.setData(result);
			String message = result ? "Cập nhật phòng thành công" : "Cập nhật phòng thất bại";
			responseDTO.setMessage(message);
			return result ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("ERROR edit room:" + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Cập nhật phòng thất bại");
			return badRequest(responseDTO);
		}
	}

	//	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/admin/deleteroom/{id}")
//	@DeleteMapping("/deleteroom/{id}")
	public ResponseEntity<?> DeleteRoom(@PathVariable("id") int id){
		//ResponseData responseData = new ResponseData();
		ResponseDTO<Boolean> responseDTO = new ResponseDTO<>();
		Boolean isDeleteSuccess = roomServiceImlp.deleteRoom(id);
		try {
			responseDTO.setData(isDeleteSuccess);
			String message = isDeleteSuccess ? "Xóa phòng thành công" : "Xóa phòng thất bại";
			responseDTO.setMessage(message);
			return isDeleteSuccess ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("ERROR DELETE ROOM: " + e.getMessage());
			responseDTO.setData(false);
			responseDTO.setMessage("Xóa phòng thất bại");
			return badRequest(responseDTO);
		}
	}

	//	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("/both/getallroom")
//	@GetMapping("/getallroom")
	public ResponseEntity<?> getAllRoom(@RequestParam(name = "page" , defaultValue = "0") String page){
		try {
			if(page.isEmpty()) {
				page = "0";
			}
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<RoomDTO> result =
					roomServiceImlp.getAllRoom(pageNum);
			String message = result.getData().size() > 0 ? "Hiển thị" : "Danh sách rỗng";
			ResponseDTO<PaginationResponseDTO<RoomDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			String message = "Lỗi hiển thị";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	//	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("/both/searchroom")
//	@GetMapping("/searchroom")
	public ResponseEntity<?> searchRoom(@RequestParam(name = "status", required = false, defaultValue = "") String status,
										@RequestParam(name = "typeid", required = false, defaultValue = "") String typeid,
										@RequestParam(name = "countofseats", required = false, defaultValue = "") String countofseats,
										@RequestParam(name = "roomname", required = false, defaultValue = "") String roomname,
										@RequestParam(name = "page", required = false, defaultValue = "0") String page){
		try {
			if(page.isEmpty()) {
				page = "0";
			}
			int pageNum = Integer.valueOf(page);
			PaginationResponseDTO<RoomDTO> result =
					roomServiceImlp.searchRoom(pageNum, status, typeid,
							countofseats, roomname);
			String message = result.getData().size() > 0 ? "Hiển thị" : "Danh sách rỗng";
			ResponseDTO<PaginationResponseDTO<RoomDTO>> response = new ResponseDTO<>(message, result);

			return result.getData().size() > 0 ? ok(response) : notFound(response);

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			String message = "Lỗi hiển thị";
			ResponseDTO<String> response = new ResponseDTO<String>(message, e.getMessage());
			return badRequest(response);
		}
	}

	//	@PreAuthorize("hasAuthority('admin') or hasAuthority('teacher')")
	@GetMapping("/both/getroom/{id}")
//	@GetMapping("/getroom/{id}")
	public ResponseEntity<?> getRoomById(@PathVariable("id") Integer id){
		ResponseDTO<RoomDTO> responseDTO = new ResponseDTO<>();
		try {
			RoomDTO roomDTO = roomServiceImlp.getRoomById(id);
			responseDTO.setData(roomDTO);
			String message = roomDTO != null ? "Thành công" : "Thất bại";
			responseDTO.setMessage(message);
			return roomDTO != null ? ok(responseDTO) : notFound(responseDTO);
		} catch (Exception e) {
			System.out.println("Error Get Room By Id: " + e.getMessage());
			responseDTO.setData(null);
			responseDTO.setMessage("Thất bại");
			return badRequest(responseDTO);
		}
	}

	@GetMapping("/file/{filename:.+}")
	public ResponseEntity <?> getFileRestaurant(@PathVariable String filename){

		Resource resource= fileServiceImpl.loadFile(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename()
						+"\"").body(resource);

	}


}

