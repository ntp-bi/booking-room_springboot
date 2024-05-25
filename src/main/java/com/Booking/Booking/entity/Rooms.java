package com.Booking.Booking.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rooms")
//@Table(name = "rooms")
public class Rooms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "room_name")
	private String roomName;
	
	@Column(name = "area")
	private double area;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "count_of_seats")
	private int countOfSeats;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private RoomTypes roomType;
	
	@OneToMany(mappedBy = "room")
	private List<Details> details;
}
