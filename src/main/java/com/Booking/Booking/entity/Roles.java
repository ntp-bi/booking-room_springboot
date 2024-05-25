package com.Booking.Booking.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "role_name")
	private String roleName;
	
	@OneToMany(mappedBy = "role")
	private List<UserAccounts> userAccounts;
}
