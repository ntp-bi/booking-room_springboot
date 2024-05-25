package com.Booking.Booking.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRoomPayload {
    private Integer roomid;
    private Integer userid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservetime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    private Integer eventid;

}
