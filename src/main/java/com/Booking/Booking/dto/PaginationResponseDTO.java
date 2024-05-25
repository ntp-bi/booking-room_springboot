package com.Booking.Booking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDTO<T> {
	
	private List<T> data;
	private int rowCount;
	private int pageCount;
}
