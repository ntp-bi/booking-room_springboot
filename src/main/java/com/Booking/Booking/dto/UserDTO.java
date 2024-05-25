package com.Booking.Booking.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private int id;
	private String fullName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDay;
	private boolean gender;
	private String accountName;
	private String photo;
}
