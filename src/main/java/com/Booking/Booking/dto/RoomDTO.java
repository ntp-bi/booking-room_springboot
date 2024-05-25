package com.Booking.Booking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

	private int id;
	private String roomName;
	private double area;
	private int status;
	private String description;
	private String image;
	private int countOfSeats;
	private int countPeopleBookingOfDate;
	private int typeId;	
	private String typeName;
}
