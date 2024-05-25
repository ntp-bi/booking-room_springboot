package com.Booking.Booking.service.impl;

import java.util.List;

import com.Booking.Booking.dto.NotificationDTO;

public interface INotificationServiceImpl {

	int sentNotification(String notificationTitle, String notificationContent, int senderId, int receiverId);
	boolean updateNotification(int id, String notificationTitle, String notificationContent);
	boolean deleteNotification(int id);
	List<NotificationDTO> getAllNotifications(int id);
	NotificationDTO getByNotificationId(int id);
}
