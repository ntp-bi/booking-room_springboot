package com.Booking.Booking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Booking.Booking.dto.NotificationDTO;
import com.Booking.Booking.entity.Notifications;
import com.Booking.Booking.entity.UserAccounts;
import com.Booking.Booking.repository.INotificationRepository;
import com.Booking.Booking.repository.IUserAccountsRepository;
import com.Booking.Booking.repository.IUserRepository;
import com.Booking.Booking.service.impl.INotificationServiceImpl;


@Service
public class NotificationService implements INotificationServiceImpl{
	
	@Autowired
	INotificationRepository notificationRepositoy;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserAccountsRepository userAccountRepository;

	@Override
	public int sentNotification(String notificationTitle, String notificationContent, int senderId, int receiverId) {
			
		try {
			UserAccounts found = userAccountRepository.findbyUserId(senderId);
			if(found == null) return 0;
			
			Notifications notifications = new Notifications();
			notifications.setNotificationTitle(notificationTitle);
			notifications.setNotificationContent(notificationContent);
			notifications.setAuthor(userRepository.getUserById(receiverId));
			notifications.setSentAt(new Date());
			
			notificationRepositoy.save(notifications);			
			return notifications.getId();
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return 0;
		}
	}

	@Override
	public boolean updateNotification(int id, String notificationTitle, String notificationContent) {
		
		boolean isUpdate = false;
		try {
			Optional<Notifications> getNotification = notificationRepositoy.findById(id);
			if(!getNotification.isPresent()) return isUpdate;
			
			Notifications notifications = getNotification.get();
			notifications.setNotificationTitle(notificationTitle);
			notifications.setNotificationContent(notificationContent);
//			notifications.setAuthor(userRepository.getUserById(userId));
			notifications.setUpdateAt(new Date());
			
			notificationRepositoy.save(notifications);			
			isUpdate = true;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return isUpdate;
		}
		return isUpdate;
	}

	@Override
	public boolean deleteNotification(int id) {
		boolean isDelete = false;
		try {
			Optional<Notifications> notification = notificationRepositoy.findById(id);
			if(!notification.isPresent()) return isDelete;
			notificationRepositoy.deleteById(id);
            isDelete = true;			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return isDelete;
		}

		return isDelete;
	}

	@Override
	public List<NotificationDTO> getAllNotifications(int id) {
		
		List<Notifications> notifications = notificationRepositoy.findAll();
		List<NotificationDTO> listNotifications = new ArrayList<>();
		
		if(notifications != null) {
			for (Notifications i : notifications) {
				NotificationDTO notification = new NotificationDTO();
				
				notification.setId(i.getId());
				notification.setNotificationTitle(i.getNotificationTitle());
				notification.setNotificationContent(i.getNotificationContent());
				notification.setSentAt(i.getSentAt());
				notification.setUpdateAt(i.getUpdateAt());
				notification.setAuthorId(i.getAuthor().getId());
				notification.setAuthorName(i.getAuthor().getFullName());
				if (i.getReceiver()!=null) {
					if(i.getReceiver().getId()== id) {
						notification.setRecieverId(i.getReceiver().getId());
						notification.setRecieverName(i.getReceiver().getFullName());
					}
				}
				else {
					notification.setRecieverId(0);
					notification.setRecieverName("Tất cả mọi người");					
				}
				
				listNotifications.add(notification);
			}
		} 	
		return listNotifications;
	}

	@Override
	public NotificationDTO getByNotificationId(int id) {
		
		Optional<Notifications> getNotification = notificationRepositoy.findById(id);
		NotificationDTO notification = new NotificationDTO();
		
		if(getNotification!= null) {
			notification.setId(getNotification.get().getId());
			notification.setNotificationTitle(getNotification.get().getNotificationTitle());
			notification.setNotificationContent(getNotification.get().getNotificationContent());
			notification.setSentAt(getNotification.get().getSentAt());
			notification.setUpdateAt(getNotification.get().getUpdateAt());
			notification.setAuthorId(getNotification.get().getAuthor().getId());
			notification.setAuthorName(getNotification.get().getAuthor().getFullName());
			if (getNotification.get().getReceiver()!=null) {
				notification.setRecieverId(getNotification.get().getReceiver().getId());
				notification.setRecieverName(getNotification.get().getReceiver().getFullName());
			}
			else {
				notification.setRecieverId(0);
				notification.setRecieverName("Tất cả mọi người");					
			}
		}
		
		return notification;
	}
	
}
