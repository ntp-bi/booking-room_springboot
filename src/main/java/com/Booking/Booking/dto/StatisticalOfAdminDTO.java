package com.Booking.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalOfAdminDTO {
    private int day;
    private int month;
    private int year;
    private int countOfRoomType;
    private List<RoomDTO> theMostRoomTypeOfBooking;
    private int theMostCountOfRoomTypeOfBooking;
    private List<RoomDTO> leastOfRoomTypeOfBooking;
    private int theLeastCountOfRoomTypeOfBooking;
    private int countOfEvent;
    private int countOfTeacher;
    private int countOfBookingRoom;
    private int countOfReturnBookingRoom;
}
