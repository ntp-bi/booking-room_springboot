package com.Booking.Booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Notifications;


@Repository
public interface INotificationRepository extends JpaRepository<Notifications, Integer>{

}
