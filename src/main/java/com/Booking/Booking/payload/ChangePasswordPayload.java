package com.Booking.Booking.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordPayload {
	private String oldPassword, newPassword, confirmPassword;
}
