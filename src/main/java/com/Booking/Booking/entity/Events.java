package com.Booking.Booking.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="event_name")
	private String eventName;
	
	@OneToMany(mappedBy = "event")
	private List<Details> details;
}
