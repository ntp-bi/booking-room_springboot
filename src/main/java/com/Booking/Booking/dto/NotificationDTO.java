package com.Booking.Booking.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	
	private int id;
	private String notificationTitle;
	private String notificationContent;
	private Date sentAt;
	private Date updateAt;
	private int authorId;	
	private String authorName;
	private int recieverId;	
	private String recieverName;
}
