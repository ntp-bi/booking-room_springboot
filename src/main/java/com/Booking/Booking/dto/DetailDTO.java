package com.Booking.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDTO {

	private int detailId;
	private String reserveTime;
	private String endTime;	
	private String returnTime;
	private String acceptTime;
	private int status;
	private int roomId;
	private String roomName;
	private int roomStatus;
	private String roomPhoto;
	private int userId;
	private String userName; 
	private int eventId;
	private String eventName;

}
