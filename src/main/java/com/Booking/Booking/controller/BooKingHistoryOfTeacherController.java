package com.Booking.Booking.controller;

import com.Booking.Booking.dto.HistoryOfTeacherDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.entity.History;
import com.Booking.Booking.security.UserContext;
import com.Booking.Booking.service.impl.IBookingRoomHistoryOfTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class BooKingHistoryOfTeacherController extends ControllerBase {
    private  final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  final SimpleDateFormat inputDateFormatBirthDay = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    IBookingRoomHistoryOfTeacherService iBookingRoomHistoryOfTeacherService;

    private HistoryOfTeacherDTO getRoomDetailsResponse(History history) {
        String formattedReserveTime = inputDateFormat.format(history.getReserveTime());
        String formattedEndTime = inputDateFormat.format(history.getEndTime());
        String formattedReturnTime = inputDateFormat.format(history.getReturnTime());
        String acceptTime = inputDateFormat.format(history.getAcceptTime());
        return new HistoryOfTeacherDTO(
                history.getId(), history.getEventId(), history.getRoomId(),
                history.getFullName(), history.getRoomName(), formattedReserveTime,acceptTime,
                formattedEndTime, formattedReturnTime, history.getStatus()
        );
    }

    // Phương thức để đặt thời gian bắt đầu của ngày thành 00:00:00
    private Date resetTimeToStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // Phương thức để đặt thời gian kết thúc của ngày thành 23:59:59
    private Date resetTimeToEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    private int LimitPageCount(int count){

        int m  = count % 10;
        int pageCount =0;
        if(m>0){
            pageCount = count /10 + 1;
        }else{
            pageCount = count/10;
        }
        return  count;
    }

    @GetMapping("/BookingHistory")
    public ResponseEntity<?> getAllHistory(){
        //int user_id = (int) httpSession.getAttribute("roleId");
        ResponseDTO<List<HistoryOfTeacherDTO>> result = new ResponseDTO<>();
        try{
            int user_id = (int) UserContext.getUserId();
            System.out.println(user_id);
            List<History> details = iBookingRoomHistoryOfTeacherService.getAllRoomDetailsOfTeacher(user_id);
            List<HistoryOfTeacherDTO> bookingRoomsHistories = new ArrayList<>();

            for (History detail :details){
                HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(detail);
                bookingRoomsHistories.add(bookingRoomHistoryOfTeacher);

            }
            result.setData(bookingRoomsHistories);
            result.setMessage(bookingRoomsHistories.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
            return bookingRoomsHistories.isEmpty() ? notFound(result) : ok(result);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            return badRequest(result);
        }

    }

    @GetMapping("BookingHistory/{detail_id}")
    public ResponseEntity<?> getHistoryById(@PathVariable(name = "detail_id") int detail_id)  {
        ResponseDTO<HistoryOfTeacherDTO> result = new ResponseDTO<>();

        try {
            //int user_id = (int) httpSession.getAttribute("roleId");
            int user_id = (int) UserContext.getUserId();
            History history = iBookingRoomHistoryOfTeacherService.getHistoryDetailsById(user_id,detail_id);
            if (history != null) {
                HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
                result.setData(bookingRoomHistoryOfTeacher);
                result.setMessage("Đã tìm thấy");
                return ok(result);
            } else {
                result.setMessage("Không tìm thấy");
                return notFound(result);
            }
        }catch (Exception e){
            result.setMessage(e.getMessage());
            return badRequest(result);
        }

    }

    //Lấy chi tiết lịch sử đặt phòng
    @GetMapping("/BookingHistory/Detail/{detail_id}")
    public ResponseEntity<?> getHistoryDetails(@PathVariable(name = "detail_id") int detail_id){
        ResponseDTO<HistoryOfTeacherDTO> result = new ResponseDTO<>();
        try {
            //int user_id = (int) httpSession.getAttribute("roleId");
            int user_id = (int) UserContext.getUserId();
            History details = iBookingRoomHistoryOfTeacherService.getHistoryDetailsById(user_id,detail_id);
            if(details !=null){
                HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = new HistoryOfTeacherDTO(
                        details.getId(), details.getId(), details.getEventId(), details.getRoomName(),
                        inputDateFormat.format(details.getReserveTime()),inputDateFormat.format(details.getEndTime()),inputDateFormat.format(details.getReturnTime()),inputDateFormat.format(details.getAcceptTime()) ,details.getStatus()
                        ,details.getDescription(),details.getPhoto(),details.getCountOfSeats(),
                        details.getTypeName(),details.getEventName(),details.getArea());
                result.setData(bookingRoomHistoryOfTeacher);
                result.setMessage("Đã tìm thấy");
                return ok(result);
            }else{
                result.setMessage("Không tìm thấy");
                return notFound(result);
            }
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            return badRequest(result);
        }
    }

    ///Tìm kiếm
    @GetMapping("BookingHistory/Search")
    public ResponseEntity<?> searchBookingRoomHistory(
            @RequestParam(name="status", required=false) Integer status,
            @RequestParam(name="key", required=false) String key,
            @RequestParam(name="bookingDay", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date bookingDay,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    )  {
        PaginationResponseDTO<HistoryOfTeacherDTO> result = new PaginationResponseDTO<>();
        try{
            Calendar calendar = Calendar.getInstance();
            //int user_id = (int) httpSession.getAttribute("roleId");
            int user_id = (int) UserContext.getUserId();

            //System.out.println(bookingDay);
            //Kiểm tra từ khóa nhận vào
            if(bookingDay == null){
                Page<History> pageData = iBookingRoomHistoryOfTeacherService.searchAllByStatusAndKey(user_id, status, key, page);
                //int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByStatusAndKeyAndDate(user_id, status, key, startDate,endDate);
                int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByStatusAndKey(user_id, status, key);
                List<History> allHistories = new ArrayList<>(pageData.getContent());
                List<HistoryOfTeacherDTO> bookingRoomHistoryOfTeachers = new ArrayList<>();
                for (History history : allHistories) {
                    HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
                    bookingRoomHistoryOfTeachers.add(bookingRoomHistoryOfTeacher);
                }
                //Kiểm tra kết quả trả về
                int pageCount = LimitPageCount(count);
                result.setData(bookingRoomHistoryOfTeachers);
                //result.setMessage(bookingRoomHistoryOfTeachers.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
                result.setPageCount(page);
                result.setRowCount(pageCount);
                return bookingRoomHistoryOfTeachers.isEmpty() ? notFound(result) : ok(result);
            }
            if (bookingDay == null) {
                calendar.set(2023, Calendar.DECEMBER, 3, 0, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                bookingDay = calendar.getTime();
            } else {
                bookingDay = resetTimeToStartOfDay(bookingDay);
            }
            if (key != null && !key.isEmpty()) {
                Page<History> pageData = iBookingRoomHistoryOfTeacherService.searchAllByStatusAndKeyAndDate1(user_id, status, key, bookingDay, page);
                //int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByStatusAndKeyAndDate(user_id, status, key, startDate,endDate);
                int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByStatusAndKeyAndDate1(user_id, status, key, bookingDay);
                List<History> allHistories = new ArrayList<>(pageData.getContent());
                List<HistoryOfTeacherDTO> bookingRoomHistoryOfTeachers = new ArrayList<>();
                for (History history : allHistories) {
                    HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
                    bookingRoomHistoryOfTeachers.add(bookingRoomHistoryOfTeacher);
                }
                //Kiểm tra kết quả trả về
                int pageCount = LimitPageCount(count);
                result.setData(bookingRoomHistoryOfTeachers);
                //result.setMessage(bookingRoomHistoryOfTeachers.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
                result.setPageCount(page);
                result.setRowCount(pageCount);
                return bookingRoomHistoryOfTeachers.isEmpty() ? notFound(result) : ok(result);
            } else {
                Page<History> pageData = iBookingRoomHistoryOfTeacherService.searchAllByStatusAndDate1(user_id,status, bookingDay, page);
                int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByDateAndStatus1(user_id, status, bookingDay);
                List<History> allHistories = new ArrayList<>(pageData.getContent());
                List<HistoryOfTeacherDTO> bookingRoomHistoryOfTeachers = new ArrayList<>();
                for (History history : allHistories) {
                    HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
                    bookingRoomHistoryOfTeachers.add(bookingRoomHistoryOfTeacher);
                }
                ///Kiểm tra kết quả trả về
                int pageCount = LimitPageCount(count);
                result.setData(bookingRoomHistoryOfTeachers);
                //result.setMessage(bookingRoomHistoryOfTeachers.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
                result.setPageCount(page);
                result.setRowCount(pageCount);
                return bookingRoomHistoryOfTeachers.isEmpty() ? notFound(result) : ok(result);
            }

        }catch (Exception e){
            //result.setMessage(e.getMessage());
            return badRequest(result);
        }

    }


//    //Tìm kiếm
//    @GetMapping("BookingHistory/Search")
//    public ResponseEntity<?> searchBookingRoomHistory(
//            @RequestParam(name="status", required=false) Integer status,
//            @RequestParam(name="key", required=false) String key,
//            @RequestParam(name="startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
//            @RequestParam(name = "page", defaultValue = "1") Integer page
//    )  {
//        PaginationResponseDTO<HistoryOfTeacherDTO> result = new PaginationResponseDTO<>();
//        try{
//            Calendar calendar = Calendar.getInstance();
//            //int user_id = (int) httpSession.getAttribute("roleId");
//            int user_id = (int) UserContext.getUserId();
//            if (startDate == null) {
//                calendar.set(2023, Calendar.DECEMBER, 3, 0, 0, 0);
//                calendar.set(Calendar.MILLISECOND, 0);
//                startDate = calendar.getTime();
//            } else {
//                startDate = resetTimeToStartOfDay(startDate);
//            }
//            //Kiểm tra từ khóa nhận vào
//            if (key != null && !key.isEmpty()) {
//                Page<History> pageData = iBookingRoomHistoryOfTeacherService.searchAllByStatusAndKeyAndDate1(user_id, status, key, startDate, page);
//                //int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByStatusAndKeyAndDate1(user_id, status, key, startDate);
//                List<History> allHistories = new ArrayList<>(pageData.getContent());
//                List<HistoryOfTeacherDTO> bookingRoomHistoryOfTeachers = new ArrayList<>();
//                for (History history : allHistories) {
//                    HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
//                    bookingRoomHistoryOfTeachers.add(bookingRoomHistoryOfTeacher);
//                }
//                //Kiểm tra kết quả trả về
//                int pageCount = LimitPageCount(0);
//                result.setData(bookingRoomHistoryOfTeachers);
//                //result.setMessage(bookingRoomHistoryOfTeachers.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
//                result.setPageCount(page);
//                result.setRowCount(pageCount);
//                return bookingRoomHistoryOfTeachers.isEmpty() ? notFound(result) : ok(result);
//            } else {
//                Page<History> pageData = iBookingRoomHistoryOfTeacherService.searchAllByStatusAndDate1(user_id,status, startDate, page);
//                int count = iBookingRoomHistoryOfTeacherService.CountOfHistoryByDateAndStatus1(user_id, status, startDate);
//                List<History> allHistories = new ArrayList<>(pageData.getContent());
//                List<HistoryOfTeacherDTO> bookingRoomHistoryOfTeachers = new ArrayList<>();
//                for (History history : allHistories) {
//                    HistoryOfTeacherDTO bookingRoomHistoryOfTeacher = getRoomDetailsResponse(history);
//                    bookingRoomHistoryOfTeachers.add(bookingRoomHistoryOfTeacher);
//                }
//                ///Kiểm tra kết quả trả về
//                int pageCount = LimitPageCount(count);
//                result.setData(bookingRoomHistoryOfTeachers);
//                //result.setMessage(bookingRoomHistoryOfTeachers.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
//                result.setPageCount(page);
//                result.setRowCount(pageCount);
//                return bookingRoomHistoryOfTeachers.isEmpty() ? notFound(result) : ok(result);
//            }
//                return null;
//        }catch (Exception e){
//            //result.setMessage(e.getMessage());
//            return badRequest(result);
//        }
//
//    }



}

