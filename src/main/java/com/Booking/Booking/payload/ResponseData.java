package com.Booking.Booking.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
	private int status = 200;
	private boolean isSuccess = true;
	private String desc;
	private Object data;
	int page;
	int totalpage;
}
