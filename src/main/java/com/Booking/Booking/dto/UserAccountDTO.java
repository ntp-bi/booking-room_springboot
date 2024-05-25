package com.Booking.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
	private int id;
	private String userName, password; // text
	private String fullName;
	private int userId; // select
	private int roleId; // select
	private String roleName;
}


