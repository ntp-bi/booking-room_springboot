package com.Booking.Booking.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.Booking.Booking.entity.*;
import com.Booking.Booking.repository.IUserAccountsRepository;
import com.Booking.Booking.security.UserContext;
import com.Booking.Booking.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Booking.Booking.convert.ConvertDate;
import com.Booking.Booking.convert.DetailConvert;
import com.Booking.Booking.dto.DetailDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.repository.IDetailRepository;
import com.Booking.Booking.repository.IEventRepository;
import com.Booking.Booking.repository.IRoomRepository;
import com.Booking.Booking.service.impl.IDetailServiceImpl;

@Service
public class DetailService implements IDetailServiceImpl {
	@Autowired
	IDetailRepository detailRepository;

	@Autowired
	IRoomRepository roomRepository;

	@Autowired
	IEventRepository eventRepository;

	@Autowired
	ConvertDate convertDate;

	@Autowired
	DetailConvert detailConvert;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	IUserAccountsRepository userAccountsRepository;

	private final int PAGE_SIZE = 10;

//	@Override
//	public Boolean bookingRoom(String userid, Integer roomid, String reservetime, String endtime,
//							   String eventid) {
//		Boolean isBookingSuccess = false;
//		try {
//			// Giải mã JWT để lấy thông tin người dùng
////			String username = jwtTokenProvider.getUsername(jwtToken);
////			System.out.println("User: " + username);
////
////			UserAccounts userAccounts = userAccountsRepository.findByUserName(username);
//
//			Integer useridValue = 0;
//			if(userid.isEmpty()) {
////				useridValue = userAccounts.getUser().getId();
//				useridValue = (int) UserContext.getUserId();
//			}else {
//				useridValue = Integer.parseInt(userid);
//			}
//
//			UserAccounts userAccounts = userAccountsRepository.findbyUserId(useridValue);
////			System.out.println("d" + userAccounts.get());
//			System.out.println("id " + useridValue);
//			Integer eventidValue = null;
//			if (!eventid.isEmpty()) {
//				eventidValue = Integer.parseInt(eventid);
//			} else {
//				eventidValue = 1;
//			}
//			System.out.println("ngày ht: " + new java.util.Date());
//			System.out.println("ngày đặt: " + reservetime);
//			System.out.println("KQ So sánh: " + convertDate.parseStringtoDate(reservetime).compareTo(new java.util.Date()));
//			if (convertDate.parseStringtoDate(reservetime).compareTo(new java.util.Date()) >= 0) {
//				String returnTime = convertDate.formatDatetoString(new java.util.Date());
//				Date[] formatted = convertDate.parseStringtoDate(reservetime, endtime, returnTime);
//				Details details = detailRepository.checkBookingRoom(roomid, formatted[0], formatted[1]);
//				System.out.println("details: " + details);
//				if (details == null) {
//
//					DetailDTO detailDTO = new DetailDTO();
//					details = new Details();
//
//					Users users = new Users();
//					users.setId(useridValue); // Sử dụng id của người dùng từ JWT
//					details.setUser(users);
//
//					Rooms rooms = new Rooms();
//					rooms.setId(roomid);
//					rooms.setStatus(1);
//					details.setRoom(rooms);
//
//					Events events = new Events();
//					events.setId(eventidValue);
//					details.setEvent(events);
//
//					details.setReserveTime(formatted[0]);
//					details.setEndTime(formatted[1]);
//					details.setReturnTime(formatted[2]);
//
//					// Kiểm tra vai trò của người dùng
//					//System.out.println("UC: " + userAccounts.get().getId() + " " + userAccounts.get().getRole().getId());
//					if (userAccounts.getRole().getId() ==1) {
//						// Nếu là admin đặt phòng
//						details.setStatus(2);
//						details.setAcceptTime(new java.util.Date());
//
//						// Từ chối tất cả các yêu cầu đặt phòng khác trong cùng khung giờ
//						List<Details> otherBookings = detailRepository.findOtherBookingsInSameTimeSlot(
//								details.getRoom().getId(), details.getReserveTime(),
//								details.getEndTime());
//
//						if (!otherBookings.isEmpty()) {
//							// Bắt đầu giao dịch cơ sở dữ liệu
//							for (Details booking : otherBookings) {
//								booking.setStatus(5); // Trạng thái từ chối
//								booking.setReturnTime(new java.util.Date());
//								detailRepository.save(booking);
//							}
//						}
//					} else {
//						details.setStatus(1);
//					}
//
////					details.setStatus(1);
//					System.out.println("ENT: " + details);
//					detailRepository.save(details);
//
//					String[] formattedDates = convertDate.formatDatetoString(formatted[0], formatted[1], formatted[2]);
//
//					detailDTO.setUserId(details.getUser().getId());
//					detailDTO.setUserName(details.getUser().getFullName());
//
//					detailDTO.setRoomId(details.getRoom().getId());
//					detailDTO.setRoomName(details.getRoom().getRoomName());
//					detailDTO.setRoomPhoto(details.getRoom().getPhoto());
//					detailDTO.setRoomStatus(details.getRoom().getStatus());
//
//					detailDTO.setEventId(details.getEvent().getId());
//					detailDTO.setEventName(details.getEvent().getEventName());
//
//					detailDTO.setReserveTime(formattedDates[0]);
//					detailDTO.setEndTime(formattedDates[1]);
//					detailDTO.setReturnTime(formattedDates[2]);
//					detailDTO.setStatus(details.getStatus());
//					detailDTO.setDetailId(details.getId());
//					isBookingSuccess = true;
//					System.out.println("UID: " + details.getUser().getId());
//					System.out.println("RID: " + details.getRoom().getId());
//
//				} else {
//					System.out.println("Phòng đã có người đặt");
//					isBookingSuccess = false;
//				}
//			}
//			else {
//				isBookingSuccess = false;
//			}
//			return isBookingSuccess;
//		} catch (Exception e) {
//			System.out.println("Lỗi nha a ơi: " + e.getMessage());
//			return isBookingSuccess;
//		}
//	}


	@Override
	public Boolean bookingRoom(String userid, Integer roomid, String reservetime, String endtime, String eventid) {
		Boolean isBookingSuccess = false;
		try {
			Integer userAccountId = 0;
			Integer useridValue = 0;
			if (userid.isEmpty()) {
				userAccountId = (int) UserContext.getUserId(); // id cua account
				useridValue = userAccountsRepository.findById(userAccountId).get().getUser().getId(); // id user
			} else {
				userAccountId = (int) UserContext.getUserId();
				useridValue = Integer.parseInt(userid);
			}
			Optional<UserAccounts> userAccounts = userAccountsRepository.findById(userAccountId);
			System.out.println("id " + useridValue);
			Integer eventidValue = null;
			if (!eventid.isEmpty()) {
				eventidValue = Integer.parseInt(eventid);
			} else {
				eventidValue = 1;
			}
			if (convertDate.parseStringtoDate(reservetime).compareTo(new java.util.Date()) >= 0) {
				String returnTime = convertDate.formatDatetoString(new java.util.Date());
				Date[] formatted = convertDate.parseStringtoDate(reservetime, endtime, returnTime);
				Details details = detailRepository.checkBookingRoom(roomid, formatted[0], formatted[1]);
				System.out.println("details: " + details);
				if (details == null) {

					DetailDTO detailDTO = new DetailDTO();
					details = new Details();

					Users users = new Users();
					users.setId(useridValue); // Sử dụng id của người dùng từ JWT
					details.setUser(users);

					Rooms rooms = new Rooms();
					rooms.setId(roomid);
					rooms.setStatus(1);
					details.setRoom(rooms);

					Events events = new Events();
					events.setId(eventidValue);
					details.setEvent(events);

					details.setReserveTime(formatted[0]);
					details.setEndTime(formatted[1]);
					details.setReturnTime(formatted[2]);

					System.out.println("RO: " + userAccounts.get().getRole().getId());
					// Kiểm tra vai trò của người dùng
					if (userAccounts.get().getRole().getId() == 1) {
						// Nếu là admin đặt phòng
						System.out.println("Bat dau thuc hien");
						details.setStatus(2);
						details.setAcceptTime(new java.util.Date());
						details.setReturnTime(convertDate.parseStringtoDate(endtime));
						details.setAcceptTime(new java.util.Date());
						// Từ chối tất cả các yêu cầu đặt phòng khác trong cùng khung giờ
						List<Details> otherBookings = detailRepository.findOtherBookingsInSameTimeSlot(
								details.getRoom().getId(), details.getReserveTime(), details.getEndTime());

						if (!otherBookings.isEmpty()) {
							// Bắt đầu giao dịch cơ sở dữ liệu
							for (Details booking : otherBookings) {
								booking.setStatus(5); // Trạng thái từ chối
								booking.setReturnTime(new java.util.Date());
								detailRepository.save(booking);
							}
						}
					} else {
						System.out.println("aaaa");
						details.setStatus(1);
						details.setReturnTime(new java.util.Date());
					}
					detailRepository.save(details);
					String[] formattedDates = convertDate.formatDatetoString(formatted[0], formatted[1], formatted[2]);

					detailDTO.setUserId(details.getUser().getId());
					detailDTO.setUserName(details.getUser().getFullName());

					detailDTO.setRoomId(details.getRoom().getId());
					detailDTO.setRoomName(details.getRoom().getRoomName());
					detailDTO.setRoomPhoto(details.getRoom().getPhoto());
					detailDTO.setRoomStatus(details.getRoom().getStatus());

					detailDTO.setEventId(details.getEvent().getId());
					detailDTO.setEventName(details.getEvent().getEventName());

					detailDTO.setReserveTime(formattedDates[0]);
					detailDTO.setEndTime(formattedDates[1]);
					detailDTO.setReturnTime(formattedDates[2]);
					detailDTO.setStatus(details.getStatus());
					detailDTO.setDetailId(details.getId());
					isBookingSuccess = true;
					System.out.println("UID: " + details.getUser().getId());
					System.out.println("RID: " + details.getRoom().getId());

				} else {
					System.out.println("Phòng đã có người đặt");
					isBookingSuccess = false;
				}
			} else {
				isBookingSuccess = false;
			}
			return isBookingSuccess;
		} catch (Exception e) {
			System.out.println("Lỗi nha a ơi: " + e.getMessage());
			return isBookingSuccess;
		}
	}


	@Override
	public int totalDetail() {
		System.out.println("Count: " + (int) detailRepository.count());
		return (int) detailRepository.count();
	}

	@Override
	public DetailDTO getDetailById(Integer detailId) {
		Optional<Details> optionalDetail = detailRepository.findById(detailId);
		Details data = new Details();
		DetailDTO detailDTO = new DetailDTO();
		if (optionalDetail.isPresent()) {
			data = optionalDetail.get();
			detailDTO = detailConvert.toDTO(data);
			return detailDTO;
		} else {
			System.out.println("Không tìm thấy chi tiết");
			return null;
		}
	}

	@Override
	public boolean acceptBookingRoom(Integer detailid) {
		Optional<Details> optionalDetail = detailRepository.findById(detailid);
		System.out.println("OP: " + optionalDetail);
		boolean isAcceptSuccess = false;

		try {
			if (optionalDetail.isPresent()) {
				Details data = optionalDetail.get();
				Rooms rooms = data.getRoom();

				Users users = data.getUser();
				Events events = data.getEvent();

				Users newUser = new Users();
				newUser.setId(users.getId());
				data.setUser(newUser);

				Events newEvent = new Events();
				newEvent.setId(events.getId());
				data.setEvent(newEvent);

				data.setAcceptTime(new java.util.Date());
				data.setReturnTime(data.getEndTime());

				if (data.getStatus() == 1) {
					data.setStatus(2);
				}
				detailRepository.save(data);
				isAcceptSuccess = true;
				System.out.println("DATTTA: " + data.getId() + " " + data.getStatus() + " " + rooms.getStatus());
			} else {
				System.out.println("Detail with ID " + detailid + " does not exist.");
			}
		} catch (Exception e) {
			System.out.println("Error Accept BookingRoom: " + e.getMessage());
		}

		return isAcceptSuccess;
	}

	@Override
	public void refuseBookingRoomSameTime(Integer detailid) {
		Optional<Details> optionalDetail = detailRepository.findById(detailid);
		// Lấy ra tất cả các chi tiết đặt phòng trong cùng khoảng thời gian và chưa được
		// xử lý
		List<Details> otherBookings = detailRepository.findOtherBookingsInSameTimeSlot(
				optionalDetail.get().getRoom().getId(), optionalDetail.get().getReserveTime(),
				optionalDetail.get().getEndTime());

		if (!otherBookings.isEmpty()) {
			// Bắt đầu giao dịch cơ sở dữ liệu
			for (Details booking : otherBookings) {
				booking.setStatus(5); // Trạng thái từ chối
				booking.setReturnTime(new java.util.Date());
				detailRepository.save(booking);
			}
		}
	}

	@Override
	public boolean refuseBookingRoom(Integer detailid) {
		Optional<Details> optionalDetail = detailRepository.findById(detailid);
		System.out.println("OP: " + optionalDetail);
		boolean isRefuseSuccess = false;

		try {
			if (optionalDetail.isPresent()) {
				Details data = optionalDetail.get();
				if (data.getStatus() == 1) {
					Rooms rooms = data.getRoom();

					Users users = data.getUser();
					Events events = data.getEvent();

					if (users != null) {
						Users newUser = new Users();
						newUser.setId(users.getId());
						data.setUser(newUser);
					}

					if (events != null) {
						Events newEvent = new Events();
						newEvent.setId(events.getId());
						data.setEvent(newEvent);
					}
					data.setStatus(5);
					data.setReturnTime(new java.util.Date());
					detailRepository.save(data);

					isRefuseSuccess = true;
					System.out.println("DATTTA: " + data.getId() + " " + data.getStatus() + " " + rooms.getStatus());
				} else {
					isRefuseSuccess = false;
				}
			} else {
				System.out.println("Detail with ID " + detailid + " does not exist.");
			}
		} catch (Exception e) {
			System.out.println("Error Refuse BookingRoom: " + e.getMessage());
		}

		return isRefuseSuccess;
	}

	@Override
	public boolean cancelBookingRoom(Integer detailid) {
		Optional<Details> optionalDetail = detailRepository.findById(detailid);
		System.out.println("OP: " + optionalDetail);
		boolean isCancelSuccess = false;

		try {
			if (optionalDetail.isPresent()) {
				Details data = optionalDetail.get();
				if (data.getStatus() == 1 || data.getStatus() == 2) {
					Rooms rooms = data.getRoom();
//					if (rooms != null) {
//						rooms.setStatus(1);
//						roomRepository.save(rooms);
//					}

					Users users = data.getUser();
					Events events = data.getEvent();

					if (users != null) {
						Users newUser = new Users();
						newUser.setId(users.getId());
						data.setUser(newUser);
					}

					if (events != null) {
						Events newEvent = new Events();
						newEvent.setId(events.getId());
						data.setEvent(newEvent);
					}

					data.setStatus(4);
					data.setReturnTime(new java.util.Date());
					detailRepository.save(data);

					isCancelSuccess = true;
					System.out.println("DATTTA: " + data.getId() + " " + data.getStatus() + " " + rooms.getStatus());
				} else {
					isCancelSuccess = false;
				}
			} else {
				System.out.println("Detail with ID " + detailid + " does not exist.");
			}
		} catch (Exception e) {
			System.out.println("Error Cancel BookingRoom: " + e.getMessage());
		}

		return isCancelSuccess;
	}

	@Override
	public boolean checkResetRoom(String detailid) {
		Optional<Details> optionalDetail = detailRepository.findById(Integer.parseInt(detailid));
		boolean isResetSuccess = false;
		try {
			if (optionalDetail.isPresent()) {
				Details data = optionalDetail.get();
				if (data.getStatus() == 2) {
					Rooms rooms = data.getRoom();
					if (rooms != null) {
						rooms.setStatus(1);
						roomRepository.save(rooms);
					}
					data.setStatus(3);
					data.setReturnTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
					data.setEndTime(data.getReturnTime());
					detailRepository.save(data);
					isResetSuccess = true;
				}
			}
		} catch (Exception e) {
			isResetSuccess = false;
			System.out.println("Error reset RoomStatus: " + e.getMessage());
		}
		return isResetSuccess;
	}

	@Override
	public List<DetailDTO> calendarbooking(int userid) {
		try {
			List<Details> list = detailRepository.calendarBooking(userid);
			List<DetailDTO> listDTOs = new ArrayList<DetailDTO>();
			for (Details data : list) {
				DetailDTO dto = new DetailDTO();
				dto = detailConvert.toDTO(data);

				listDTOs.add(dto);
			}
			return listDTOs;
		} catch (Exception e) {
			System.out.println("ERROR Calendar: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean updateBookingRoomWhenWatingAccept(Integer detailid, Integer roomid, Integer eventid,
													 String reversetime, String endtime) {
		boolean isUpdateSuccess = false;
		try {
			Optional<Details> optionalDetails = detailRepository.findById(detailid);
			if (optionalDetails.isPresent()) {
				Details data = optionalDetails.get();
				if (data.getStatus() == 1) {
					Details details = detailRepository.checkBookingRoom(roomid,
							convertDate.parseStringtoDate(reversetime), convertDate.parseStringtoDate(endtime));
					if(details == null) {
						data.setRoom(roomRepository.findByRoomId(roomid));
						System.out.println("R: " + data.getRoom().getId());
						data.setEvent(eventRepository.findById(eventid).get());
						System.out.println("E: " + data.getEvent().getId());
						Date[] formated = convertDate.parseStringtoDate(reversetime, endtime, reversetime);
						System.out.println("ketqua: " + convertDate.parseStringtoDate(reversetime).compareTo(new java.util.Date()));
						if (convertDate.parseStringtoDate(reversetime).compareTo(new java.util.Date()) >= 0
								&& reversetime.compareTo(endtime) < 0) {
							data.setReserveTime(formated[0]);
							data.setEndTime(formated[1]);
							data.setReturnTime(formated[1]);
							detailRepository.save(data);
							isUpdateSuccess = true;
						}else {
							isUpdateSuccess = false;
						}

					}else {
						System.out.println("Không thể chọn phòng này vì đã có người đặt");
						isUpdateSuccess = false;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR UPDATE: " + e.getMessage());
			isUpdateSuccess = false;
		}
		return isUpdateSuccess;
	}

	@Override
	public PaginationResponseDTO<DetailDTO> getAllDetail(Integer page) {

		try {
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);

			List<Details> details = detailRepository.findAll(pageable).getContent();
			List<DetailDTO> detailDTOs = new ArrayList<DetailDTO>();
			for (Details data : details) {
				DetailDTO detailDTO = new DetailDTO();
				detailDTO = detailConvert.toDTO(data);

				detailDTOs.add(detailDTO);
			}

			int rowCount = detailRepository.rowCount();
			int pageCount = rowCount / PAGE_SIZE;
			if (rowCount % PAGE_SIZE > 0)
				++pageCount;

			return new PaginationResponseDTO<DetailDTO>(detailDTOs, rowCount, pageCount);
		} catch (Exception e) {
			System.out.println("lỗi lấy danh sách chi tiết: " + e.getMessage());
			return null;
		}
	}

	@Override
	public PaginationResponseDTO<DetailDTO> searchRoomDetail(Integer page, String typeid,
															 String username, String roomname, String bookingtime, String detailstatus) {
		try {

			Integer typeidValue = null;
			if (!typeid.isEmpty()) {
				typeidValue = Integer.parseInt(typeid);
			}

			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			Date bookingtimeValue = null;
			if (!bookingtime.isEmpty()) {
				bookingtimeValue = formatDate.parse(bookingtime);
			}

			Integer detailstatusValue = null;
			if (!detailstatus.isEmpty()) {
				detailstatusValue = Integer.parseInt(detailstatus);
			}

			List<DetailDTO> detailDTOs = new ArrayList<DetailDTO>();

			Pageable pageable = PageRequest.of(page, PAGE_SIZE);

			for (Details data : detailRepository.searchRoomDetail(pageable, typeidValue, roomname,
					username, bookingtimeValue, detailstatusValue)) {
				DetailDTO detailDTO = new DetailDTO();
				detailDTO = detailConvert.toDTO(data);

				detailDTOs.add(detailDTO);
			}

			int rowCount = detailRepository.rowCount(typeidValue, roomname, username,
					bookingtimeValue, detailstatusValue);
			int pageCount = rowCount / PAGE_SIZE;
			if (rowCount % PAGE_SIZE > 0)
				++pageCount;

			return new PaginationResponseDTO<DetailDTO>(detailDTOs, rowCount, pageCount);
		} catch (Exception e) {
			System.out.println("lỗi lấy danh sách chi tiết: " + e.getMessage());
			return null;
		}
	}

	@Override
	public PaginationResponseDTO<DetailDTO> getDetailNonAccept(Integer page) {
		try {
			Pageable pageable = PageRequest.of(page, PAGE_SIZE);

			List<Details> details = detailRepository.findByStatus(1, pageable);
			List<DetailDTO> detailDTOs = new ArrayList<DetailDTO>();
			for (Details data : details) {
				DetailDTO detailDTO = new DetailDTO();
				detailDTO = detailConvert.toDTO(data);

				detailDTOs.add(detailDTO);
			}

			int rowCount = detailRepository.rowCountNonAccept();
			int pageCount = rowCount / PAGE_SIZE;
			if (rowCount % PAGE_SIZE > 0)
				++pageCount;

			return new PaginationResponseDTO<DetailDTO>(detailDTOs, rowCount, pageCount);
		} catch (Exception e) {
			System.out.println("lỗi lấy danh sách chi tiết: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean deleteHistoryDetail(int id) {
		int rowsDeleted = detailRepository.deleteHistoryDetailById(id);
		return rowsDeleted > 0;

	}
}
