package com.Booking.Booking.service.impl;

import com.Booking.Booking.entity.History;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IBookingRoomsHistoryOfAdminService {
    Page<History> getAllHistory(Integer page);

    Page<History> searchAllByStatusAndNameAndDateRange(Integer status, String key, Date startDate, Date endDate, Integer page);

    int CountOfHistory(Integer status, String key, Date startDate, Date endDate);

    Page<History> searchAllByStatusAndDateRange(Integer status, Date startDate, Date endDate, Integer page);

    int CountOfHistoryNotKey(Integer status, Date startDate, Date endDate);

    History getHistoryDetailsById(int detailId);

    int CountOfHistory();

    List<Integer> getEvenByYear(Integer year);

    int countOfEventByYear(Integer year);

    List<Integer> getRoomID(Integer year);

    int countOfRoom(Integer year);

    List<Integer> theMostRoomTypeBookingIDByYear(Integer year);

    History getRoomById(Integer roomId);

    int TheMostCountOfRoomTypeOfBookingByYear(Integer year);

    List<Integer> theLeastRoomTypeBookingIdByYear(Integer year);


    int TheLeastCountOfRoomTypeOfBookingByYear(Integer year);

    List<Integer> getTeacherByYear(Integer year);

    int CountOfBookingRoomByYear(Integer year);

    int CountOfReturnBookingRoomByYear(Integer year);

    List<Integer> getEvenByMonth(Integer month, Integer year);

    List<Integer> getRoomTypeByMonth(Integer month, Integer year);

    List<Integer> theMostRoomTypeBookingIDByMonth(Integer month, Integer year);

    int TheMostCountOfRoomTypeOfBookingByMonth(Integer month, Integer year);

    List<Integer> theLeastRoomTypeBookingIdByMonth(Integer month, Integer year);

    int TheLeastCountOfRoomTypeOfBookingByMonth(Integer month, Integer year);

    List<Integer> getUserIdByMonth(Integer month, Integer year);

    int CountOfBookingRoomByMonth(Integer month, Integer year);

    int CountOfReturnBookingRoomByMonth(Integer month, Integer year);

    List<Integer> getEvenByDay(Integer day, Integer month, Integer year);

    List<Integer> getRoomTypeByDay(Integer day, Integer month, Integer year);

    List<Integer> theMostRoomTypeBookingIDByDay(Integer day, Integer month, Integer year);

    int TheMostCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year);

    List<Integer> theLeastRoomTypeBookingIdByDay(Integer day, Integer month, Integer year);

    int TheLeastCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year);

    List<Integer> getTeacherByDay(Integer day, Integer month, Integer year);

    int CountOfBookingRoomByDay(Integer day, Integer month, Integer year);

    int CountOfReturnBookingRoomByDay(Integer day, Integer month, Integer year);

    boolean deleteHistoryDetail(int id);

    History findByRoomId(Integer roomId);

    void save(History history);

}
