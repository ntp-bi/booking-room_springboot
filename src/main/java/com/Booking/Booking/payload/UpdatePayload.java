package com.Booking.Booking.payload;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePayload {
    private Integer id;
    private Integer roomid;
    private Integer eventid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservetime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;

}
