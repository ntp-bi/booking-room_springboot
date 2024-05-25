package com.Booking.Booking.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "birth_day")
	private Date birthDay;

	@Column(name = "gender")
	private boolean gender;

	@OneToOne(mappedBy = "user")
	private UserAccounts account;

	@Column(name = "photo")
	private String photo;

	@OneToMany(mappedBy = "author")
	private List<Notifications> notifications;

	@OneToMany(mappedBy = "receiver")
	private List<Notifications> receiveNotifications;

	@OneToMany(mappedBy = "user")
	private List<Details> details;
	
}
