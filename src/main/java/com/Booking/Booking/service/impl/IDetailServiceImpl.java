package com.Booking.Booking.service.impl;

import java.util.List;

import com.Booking.Booking.dto.DetailDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;


public interface IDetailServiceImpl {
	Boolean bookingRoom(String userid, Integer roomid, String reservetime, String endtime,
						String eventid);

	DetailDTO getDetailById(Integer detailId);

	int totalDetail();

	// xử lý đặt hủy
	//TH xác nhận
	boolean acceptBookingRoom(Integer detailid);

	//TH từ chối
	boolean refuseBookingRoom(Integer detailid);

	//TH hủy
	boolean cancelBookingRoom(Integer detailid);


	// reset Phòng khi đã đến ngày trả (end time hoặc return time)
	boolean checkResetRoom(String detailid);

	List<DetailDTO> calendarbooking(int userid);

	void refuseBookingRoomSameTime(Integer detailid);

	boolean updateBookingRoomWhenWatingAccept(Integer detailid, Integer roomid, Integer eventid,
											  String reversetime , String endtime);


	PaginationResponseDTO<DetailDTO> getAllDetail(Integer page);
	PaginationResponseDTO<DetailDTO> searchRoomDetail(Integer page, String typeid,
													  String username, String roomname, String bookingtime, String detailstatus);

	PaginationResponseDTO<DetailDTO> getDetailNonAccept(Integer page);

	boolean deleteHistoryDetail(int id);
}
