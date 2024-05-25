package com.Booking.Booking.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="notification_title")
	private String notificationTitle;
	
	@Column(name ="notification_content")
	private String notificationContent;
	
	@Column(name = "sent_at")
	private Date sentAt;
	
	@Column(name = "update_at")
	private Date updateAt;
	
	@ManyToOne
	@JoinColumn(name ="sender_id")
	private Users author;
	
	@ManyToOne
	@JoinColumn(name ="receiver_id")
	private Users receiver;
}
