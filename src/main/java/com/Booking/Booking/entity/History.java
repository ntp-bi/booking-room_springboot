package com.Booking.Booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "user_id")
    private int userId ;
    @Column(name = "room_id")
    private int roomId;
    @Column(name = "event_id")
    private int eventId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "accept_time")
    private Date acceptTime;
    @Column(name = "reserve_time")
    private Date reserveTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "return_time")
    private Date returnTime;
    @Column(name = "status")
    private int status;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "birthDay")
    private Date birthDay;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "area")
    private double area;
    @Column(name = "room_status")
    private int roomStatus;
    @Column(name = "description")
    private String description;
    @Column(name = "photo")
    private String photo;
    @Column(name = "count_of_seats")
    private int countOfSeats;
    @Column(name = "type_name")
    private String typeName;
}
