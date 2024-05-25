package com.Booking.Booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOfTeacherDTO {
    private int id ;
    private int room_Id;
    private int eventId;
    private String roomName;
    private String reserveTime;
    private String endTime;
    private String returnTime;
    private String acceptTime;
    private int status;
    private String eventName;
    private double area;
    private String description;
    private String photo;
    private int countOfSeats;
    private String typeName;

    public HistoryOfTeacherDTO(int id, int eventId, int roomId, String fullName, String roomName,
                               String reserveTime, String acceptTime, String endTime,
                               String returnTime, int status) {
        this.id = id;
        this.room_Id = room_Id;
        this.eventId = eventId;
        this.roomName = roomName;
        this.reserveTime = reserveTime;
        this.endTime = endTime;
        this.returnTime = returnTime;
        this.acceptTime = acceptTime;
        this.status = status;
        this.description = description;
        this.photo = photo;
        this.countOfSeats = countOfSeats;
        this.typeName = typeName;
        this.eventName = eventName;
        this.area = area;
    }

    public HistoryOfTeacherDTO(int id, int room_Id, int eventId,
                               String roomName, String reserveTime, String endTime,
                               String returnTime, String acceptTime,int status, String description,
                               String photo, int countOfSeats, String typeName,
                               String eventName, double area) {
        this.id = id;
        this.room_Id = room_Id;
        this.eventId = eventId;
        this.roomName = roomName;
        this.reserveTime = reserveTime;
        this.endTime = endTime;
        this.returnTime = returnTime;
        this.acceptTime = acceptTime;
        this.status = status;
        this.description = description;
        this.photo = photo;
        this.countOfSeats = countOfSeats;
        this.typeName = typeName;
        this.eventName = eventName;
        this.area = area;
    }
}
