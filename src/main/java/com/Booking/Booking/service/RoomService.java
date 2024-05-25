package com.Booking.Booking.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Booking.Booking.convert.RoomConvert;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.RoomDTO;
import com.Booking.Booking.entity.RoomTypes;
import com.Booking.Booking.entity.Rooms;
import com.Booking.Booking.repository.IDetailRepository;
import com.Booking.Booking.repository.IRoomRepository;
import com.Booking.Booking.service.impl.IFileServiceImpl;
import com.Booking.Booking.service.impl.IRoomServiceImpl;

@Service
public class RoomService implements IRoomServiceImpl {

	private final int PAGE_SIZE = 10;


	@Autowired
	IRoomRepository roomRepository;

	@Autowired
	IFileServiceImpl fileServiceImpl;

	@Autowired
	RoomConvert roomConvert;

	@Autowired
	IDetailRepository detailRepository;

	@Override
	public boolean insertRoom(MultipartFile file, String roomname, Double area, Integer status,
							  String description,	Integer countofseats, Integer typeid) {

		boolean isInsertSuccess = false;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			boolean isSaveFileSuccess = true;
			String photoFilename = "nophotoroom.jpg";

			if (file != null && !file.isEmpty()) {
				isSaveFileSuccess = fileServiceImpl.saveFile(file);
				String formattedDate = dateFormat.format(new java.util.Date());
				photoFilename = formattedDate + "_" + file.getOriginalFilename();
			}
			Rooms rooms = roomRepository.findByRoomNameAndTypeId(roomname, typeid);
			System.out.println("roooms: " + rooms);
			if(rooms == null) {
				if (isSaveFileSuccess) {
					rooms = new Rooms();
					rooms.setRoomName(roomname);
					rooms.setStatus(status);
					rooms.setArea(area);
					rooms.setCountOfSeats(countofseats);
					rooms.setDescription(description);
					rooms.setPhoto(photoFilename);
					RoomTypes roomTypes = new RoomTypes();
					roomTypes.setId(typeid);
					rooms.setRoomType(roomTypes);

					roomRepository.save(rooms);
					isInsertSuccess = true;
				}else {
					System.out.println("Phòng này đã tồn tại. Không thể thêm");
					isInsertSuccess = false;
				}
			}
		} catch (Exception e) {
			System.out.println("Error insert room: " + e.getMessage());
		}

		return isInsertSuccess;
	}

	@Override
	public boolean editRoom(Integer roomid, MultipartFile file, String roomname, Double area, Integer status,
							String description, Integer countofseats, Integer typeid) {
		boolean isEditSuccess = false;
		Optional<Rooms> optionalRoom = roomRepository.findById(roomid);
		Rooms data = new Rooms();
		try {
			boolean saveFileSuccess = true;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = "";
			if (optionalRoom.isPresent()) {
				data = optionalRoom.get();
				fileName = data.getPhoto();
				if (file != null && !file.isEmpty()) {
					saveFileSuccess = fileServiceImpl.saveFile(file);
					String formattedDate = dateFormat.format(new java.util.Date());
					fileName = formattedDate + "_" + file.getOriginalFilename();
				}
				if (saveFileSuccess) {
					data.setPhoto(fileName);
				}
				data.setRoomName(roomname);
				data.setDescription(description);
				data.setCountOfSeats(countofseats);
				data.setArea(area);
				data.setStatus(status);
				RoomTypes roomTypes = new RoomTypes();
				roomTypes.setId(typeid);
				data.setRoomType(roomTypes);
				roomRepository.save(data);
				isEditSuccess = true;

			} else {
				System.out.println("Room with ID " + roomid + " does not exist.");
				isEditSuccess = false;
			}
		} catch (Exception e) {
			System.out.println("Error Editing Room: " + e.getMessage());
			isEditSuccess = false;
		}
		return isEditSuccess;
	}

	@Override
	public boolean deleteRoom(Integer roomid) {
		boolean isDeleteSuccess = false;
		try {
			Optional<Rooms> optionalRoom = roomRepository.findById(roomid);
			if (optionalRoom.isPresent()) {
				roomRepository.deleteById(roomid);
				isDeleteSuccess = true;
			} else {
				System.out.println("Room with ID " + roomid + " does not exist.");
			}
		} catch (Exception e) {
			isDeleteSuccess = false;
			System.out.println("Error deleting room with ID " + roomid + ": " + e.getMessage());
			e.printStackTrace(); // In ra stack trace để xem lỗi cụ thể
		}
		return isDeleteSuccess;
	}



	@Override
	public int totalRoom() {
		System.out.println("Count: " + (int) roomRepository.count());
		return (int) roomRepository.count();
	}

	@Override
	public RoomDTO getRoomById(Integer id) {
		Optional<Rooms> optionalRoom = roomRepository.findById(id);
		Rooms data = new Rooms();
		RoomDTO roomDTO = new RoomDTO();
		if(optionalRoom.isPresent()) {
			data = optionalRoom.get();
			roomDTO = roomConvert.toDTO(data);
			return roomDTO;
		}
		else {
			System.out.println("Không tìm thấy phòng");
			return null;
		}
	}

	@Override
	public PaginationResponseDTO<RoomDTO> searchRoom(Integer page, String status, String typeid,
													 String countofseats, String roomname) {
		try {

			Integer statusValue = null;
			if (!status.isEmpty()) {
				statusValue = Integer.parseInt(status);
			}

			Integer typeidValue = null;
			if (!typeid.isEmpty()) {
				typeidValue = Integer.parseInt(typeid);
			}

			Integer countofseatsValue = null;
			if (!countofseats.isEmpty()) {
				countofseatsValue = Integer.parseInt(countofseats);
			}

			Pageable pageable = PageRequest.of(page, PAGE_SIZE);

			List<RoomDTO> roomDTOs = new ArrayList<RoomDTO>();
			for(Rooms data : roomRepository.searchRoom(pageable, statusValue, typeidValue,
					countofseatsValue, roomname)) {
				RoomDTO roomDTO = new RoomDTO();
				roomDTO = roomConvert.toDTO(data);

				roomDTOs.add(roomDTO);
			}

			int rowCount = roomRepository.rowcount(statusValue, typeidValue, countofseatsValue, roomname);
			int pageCount = rowCount / PAGE_SIZE;
			if (rowCount % PAGE_SIZE > 0)
				++pageCount;

			return new PaginationResponseDTO<RoomDTO>(roomDTOs, rowCount, pageCount);
		} catch (Exception e) {
			System.out.println("lỗi lấy danh sách chi tiết: " + e.getMessage());
			return null;
		}
	}

	@Override
	public PaginationResponseDTO<RoomDTO> getAllRoom(Integer page) {
		try {
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);

			List<RoomDTO> roomDTOs = new ArrayList<RoomDTO>();
			for(Rooms data : roomRepository.findAll(pageable).getContent()) {
				RoomDTO roomDTO = new RoomDTO();
				roomDTO = roomConvert.toDTO(data);
				Integer count = detailRepository.quantityUserBookingRoomOfDate(data.getId(), new java.util.Date());
				if(count == null) {
					count = 0;
				}
				roomDTO.setCountPeopleBookingOfDate(count);
				roomDTOs.add(roomDTO);
			}

			int rowCount = roomRepository.rowCount();
			int pageCount = rowCount / PAGE_SIZE;
			if (rowCount % PAGE_SIZE > 0)
				++pageCount;

			return new PaginationResponseDTO<RoomDTO>(roomDTOs, rowCount, pageCount);
		} catch (Exception e) {
			System.out.println("lỗi lấy danh sách chi tiết: " + e.getMessage());
			return null;
		}
	}
}
