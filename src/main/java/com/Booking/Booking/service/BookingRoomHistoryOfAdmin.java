package com.Booking.Booking.service;

import com.Booking.Booking.entity.History;
import com.Booking.Booking.repository.IHistoryOfAdminRepository;
import com.Booking.Booking.service.impl.IBookingRoomsHistoryOfAdminService;
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
public class BookingRoomHistoryOfAdmin implements IBookingRoomsHistoryOfAdminService {
    private final IHistoryOfAdminRepository historyRepository;

    private final int pageSize = 10;

    @Override
    public Page<History> getAllHistory(Integer page) {
        Pageable pageable = PageRequest.of(page-1,pageSize);
        return historyRepository.findAll(pageable);
    }

    @Override
    public Page<History> searchAllByStatusAndNameAndDateRange(Integer status, String key, Date startDate, Date endDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return historyRepository.searchAllByStatusAndNameAndDateRange(status, key, startDate, endDate, pageable);
    }

    @Override
    public int CountOfHistory(Integer status, String key, Date startDate, Date endDate) {
        return historyRepository.countOfHistoryBookingRoom(status,key,startDate,endDate);
    }

    @Override
    public Page<History> searchAllByStatusAndDateRange(Integer status, Date startDate, Date endDate, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return historyRepository.searchAllByStatusAndDateRange(status, startDate, endDate, pageable);
    }

    @Override
    public int CountOfHistoryNotKey(Integer status, Date startDate, Date endDate) {
        return historyRepository.CountOfHistoryNotKey(status,startDate,endDate);
    }

    @Override
    public History getHistoryDetailsById(int detailId) {
        Optional<History> roomOptional = historyRepository.findById(detailId);
        return roomOptional.orElse(null);
    }

    @Override
    public int CountOfHistory() {
        return (int) historyRepository.count();
    }

    @Override
    public List<Integer> getEvenByYear(Integer year) {
        return historyRepository.getEvenByYear(year);
    }

    @Override
    public int countOfEventByYear(Integer year) {
        return historyRepository.countOfEventByYear(year);
    }

    @Override
    public List<Integer> getRoomID(Integer year) {
        return historyRepository.getRoomID(year);
    }

    @Override
    public int countOfRoom(Integer year) {
        return historyRepository.countOfRoom(year);
    }

    @Override
    public List<Integer> theMostRoomTypeBookingIDByYear(Integer year) {
        return historyRepository.theMostRoomTypeBookingIDByYear(year);
    }

    @Override
    public History getRoomById(Integer roomId) {
        return historyRepository.findByRoomId(roomId);
    }

    @Override
    public int TheMostCountOfRoomTypeOfBookingByYear(Integer year) {
        Integer count  = historyRepository.TheMostCountOfRoomTypeOfBookingByYear(year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> theLeastRoomTypeBookingIdByYear(Integer year) {
        return historyRepository.theLeastRoomTypeBookingIdByYear(year);
    }

    @Override
    public int TheLeastCountOfRoomTypeOfBookingByYear(Integer year) {
        Integer count  = historyRepository.TheLeastCountOfRoomTypeOfBookingByYear(year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> getTeacherByYear(Integer year) {
        return historyRepository.getTeacherByYear(year);
    }

    @Override
    public int CountOfBookingRoomByYear(Integer year) {
        return historyRepository.CountOfBookingRoomByYear(year);
    }

    @Override
    public int CountOfReturnBookingRoomByYear(Integer year) {
        return historyRepository.CountOfReturnBookingRoomByYear(year);
    }

    @Override
    public List<Integer> getEvenByMonth(Integer month, Integer year) {
        return historyRepository.getEvenByMonth(month,year);
    }

    @Override
    public List<Integer> getRoomTypeByMonth(Integer month, Integer year) {
        return historyRepository.getRoomTypeByMonth(month,year);
    }

    @Override
    public List<Integer> theMostRoomTypeBookingIDByMonth(Integer month, Integer year) {
        return historyRepository.theMostRoomTypeBookingIDByMonth(month,year);
    }

    @Override
    public int TheMostCountOfRoomTypeOfBookingByMonth(Integer month, Integer year) {
        Integer count  = historyRepository.TheMostCountOfRoomTypeOfBookingByMonth(month,year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> theLeastRoomTypeBookingIdByMonth(Integer month, Integer year) {
        return historyRepository.theLeastRoomTypeBookingIdByMonth(month,year);
    }

    @Override
    public int TheLeastCountOfRoomTypeOfBookingByMonth(Integer month, Integer year) {
        Integer count  = historyRepository.TheLeastCountOfRoomTypeOfBookingByMonth(month,year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> getUserIdByMonth(Integer month, Integer year) {
        return historyRepository.getUserIdByMonth(month,year);
    }

    @Override
    public int CountOfBookingRoomByMonth(Integer month, Integer year) {
        return historyRepository.CountOfBookingRoomByMonth(month,year);
    }

    @Override
    public int CountOfReturnBookingRoomByMonth(Integer month, Integer year) {
        return historyRepository.CountOfReturnBookingRoomByMonth(month,year);
    }

    @Override
    public List<Integer> getEvenByDay(Integer day, Integer month, Integer year) {
        return historyRepository.getEvenByDay(day,month,year);
    }

    @Override
    public List<Integer> getRoomTypeByDay(Integer day, Integer month, Integer year) {
        return historyRepository.getRoomTypeByDay(day,month,year);
    }

    @Override
    public List<Integer> theMostRoomTypeBookingIDByDay(Integer day, Integer month, Integer year) {
        return historyRepository.theMostRoomTypeBookingIDByDay(day,month,year);
    }

    @Override
    public int TheMostCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year) {
        Integer count  = historyRepository.TheMostCountOfRoomTypeOfBookingByDay(day,month,year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> theLeastRoomTypeBookingIdByDay(Integer day, Integer month, Integer year) {
        return historyRepository.theLeastRoomTypeBookingIdByDay(day,month,year);
    }

    @Override
    public int TheLeastCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year) {
        Integer count  = historyRepository.TheLeastCountOfRoomTypeOfBookingByDay(day,month,year);
        if(count !=null){
            return count;
        }
        return 0;
    }

    @Override
    public List<Integer> getTeacherByDay(Integer day, Integer month, Integer year) {
        return historyRepository.getTeacherByDay(day,month,year);
    }

    @Override
    public int CountOfBookingRoomByDay(Integer day, Integer month, Integer year) {
        return historyRepository.CountOfBookingRoomByDay(day,month,year);
    }

    @Override
    public int CountOfReturnBookingRoomByDay(Integer day, Integer month, Integer year) {
        return historyRepository.CountOfReturnBookingRoomByDay(day,month,year);
    }

    @Override
    public boolean deleteHistoryDetail(int id) {
        int rowsDeleted = historyRepository.deleteHistoryDetailById(id);
        return rowsDeleted > 0;
    }

    @Override
    public History findByRoomId(Integer roomId) {
        return historyRepository.findByRoomId(roomId);
    }

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

//    @Override
//    public List<Integer> theRoomBooking(int roomId, Date reserveTime, Date endTime) {
//        return historyRepository.theRoomBooking(roomId,reserveTime,endTime);
//    }
}
