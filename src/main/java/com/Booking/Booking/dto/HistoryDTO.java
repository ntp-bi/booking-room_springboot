package com.Booking.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private int id ;
    private int userId ;
    private int room_Id;
    private int eventId;
    private String fullName;
    private String roomName;
    private String reserveTime;
    private String endTime;
    private String returnTime;
    private String acceptTime;
    private int status;
    private String eventName;
    private String birthDay;
    private Boolean gender;
    private double area;
    private int roomStatus;
    private String description;
    private String photo;
    private int countOfSeats;
    private String typeName;




    public HistoryDTO(int id, int userId, int room_Id, int eventId, String roomName, String fullName, String reserveTime, String endTime, String returnTime,String acceptTime, int status, String description, String photo, int countOfSeats, String typeName, String birthDay, Boolean gender, String eventName, double area) {
        this.id = id;
        this.userId = userId;
        this.room_Id = room_Id;
        this.eventId = eventId;
        this.roomName = roomName;
        this.fullName = fullName;
        this.reserveTime = reserveTime;
        this.endTime = endTime;
        this.returnTime = returnTime;
        this.acceptTime = acceptTime;
        this.status = status;
        this.description = description;
        this.photo = photo;
        this.countOfSeats = countOfSeats;
        this.typeName = typeName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.eventName = eventName;
        this.area = area;
    }
    public HistoryDTO(int id, int userId, int room_Id, int eventId, String fullName, String roomName, String reserveTime,String acceptTime, String endTime, String returnTime, int status) {
        this.id = id;
        this.userId = userId;
        this.room_Id = room_Id;
        this.eventId = eventId;
        this.fullName = fullName;
        this.roomName = roomName;
        this.reserveTime = reserveTime;
        this.endTime = endTime;
        this.acceptTime = acceptTime;
        this.returnTime = returnTime;
        this.status = status;
    }
}
