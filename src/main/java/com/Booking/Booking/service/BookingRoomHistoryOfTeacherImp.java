package com.Booking.Booking.service;

import com.Booking.Booking.entity.History;
import com.Booking.Booking.repository.HistoryOfTeacherRepository;
import com.Booking.Booking.service.impl.IBookingRoomHistoryOfTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingRoomHistoryOfTeacherImp implements IBookingRoomHistoryOfTeacherService {
    private final HistoryOfTeacherRepository historyRepository;

    private final int pageSize = 10;


    @Override
    public List<History> getAllRoomDetailsOfTeacher(int userId) {
        return historyRepository.findByUserId(userId);
    }

    @Override
    public History getHistoryDetailsById(int userId, int detailId)  {
        Optional<History> roomOptional = historyRepository.findByDetailId(userId, detailId);
        return roomOptional.orElse(null); // Trả về null nếu không tìm thấy lịch sử
    }

    @Override
    public Page<History> searchAllByStatusAndKeyAndDate(int id, Integer status, String key, Date startDate, Date endDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return historyRepository.searchAllByStatusAndKeyAndDate(id,status,key, startDate,endDate,pageable);
    }

    @Override
    public int CountOfHistoryByStatusAndKeyAndDate(int id, Integer status, String key, Date startDate, Date endDate) {
        return historyRepository.CountOfHistoryByStatusAndKeyAndDate(id,status,key,startDate,endDate);
    }

    @Override
    public Page<History> searchAllByStatusAndDate(int id, Integer status, Date startDate, Date endDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return historyRepository.searchAllByStatusAndDateOfTeacher(id,status, startDate,endDate, pageable);
    }

    @Override
    public int CountOfHistoryByDateAndStatus(int id, Integer status, Date startDate, Date endDate) {
        return historyRepository.CountOfHistoryByDateAndStatus(id,status,startDate,endDate);
    }

    @Override
    public Page<History> searchAllByStatusAndKeyAndDate1(int userId, Integer status, String key, Date startDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return historyRepository.searchAllByStatusAndKeyAndDate1(userId,status,key, startDate,pageable);
    }

    @Override
    public int CountOfHistoryByStatusAndKeyAndDate1(int userId, Integer status, String key, Date startDate) {
        return historyRepository.CountOfHistoryByStatusAndKeyAndDate1(userId,key,status,startDate);
    }

    @Override
    public Page<History> searchAllByStatusAndDate1(int userId, Integer status, Date startDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return historyRepository.searchAllByStatusAndDateOfTeacher1(userId,status, startDate, pageable);
    }

    @Override
    public int CountOfHistoryByDateAndStatus1(int userId, Integer status, Date startDate) {
        return historyRepository.CountOfHistoryByDateAndStatus1(userId,status,startDate);
    }

    @Override
    public Page<History> searchAllByStatusAndKey(int userId, Integer status, String key, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return historyRepository.searchAllByStatusOfTeacher(userId,status,key, pageable);
    }

    @Override
    public int CountOfHistoryByStatusAndKey(int userId, Integer status, String key) {
        return historyRepository.CountOfHistoryByStatusAndKey(userId,status,key);
    }
}
