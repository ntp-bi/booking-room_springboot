package com.Booking.Booking.entity;


import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="room_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "type_name")
	private String typeName;
	
	@OneToMany(mappedBy = "roomType")
	private List<Rooms> room;
}
