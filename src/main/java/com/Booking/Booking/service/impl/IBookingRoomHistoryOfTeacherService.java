package com.Booking.Booking.service.impl;


import com.Booking.Booking.entity.History;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IBookingRoomHistoryOfTeacherService {
    List<History> getAllRoomDetailsOfTeacher(int userId);

    History getHistoryDetailsById(int userId, int detailId);

    Page<History> searchAllByStatusAndKeyAndDate(int id, Integer status, String key, Date startDate, Date endDate, Integer page);

    int CountOfHistoryByStatusAndKeyAndDate(int id, Integer status, String key, Date startDate, Date endDate);

    Page<History> searchAllByStatusAndDate(int id, Integer status, Date startDate, Date endDate, Integer page);

    int CountOfHistoryByDateAndStatus(int id, Integer status, Date startDate, Date endDate);

    Page<History> searchAllByStatusAndKeyAndDate1(int userId, Integer status, String key, Date startDate, Integer page);

    int CountOfHistoryByStatusAndKeyAndDate1(int userId, Integer status, String key, Date startDate);

    Page<History> searchAllByStatusAndDate1(int userId, Integer status, Date startDate, Integer page);

    int CountOfHistoryByDateAndStatus1(int userId, Integer status, Date startDate);

    Page<History> searchAllByStatusAndKey(int userId, Integer status, String key, Integer page);

    int CountOfHistoryByStatusAndKey(int userId, Integer status, String key);
}
