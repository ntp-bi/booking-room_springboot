package com.Booking.Booking.controller;

import com.Booking.Booking.dto.HistoryDTO;
import com.Booking.Booking.dto.PaginationResponseDTO;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.RoomDTO;
import com.Booking.Booking.entity.Details;
import com.Booking.Booking.entity.History;
import com.Booking.Booking.service.impl.IBookingRoomsHistoryOfAdminService;
import com.Booking.Booking.service.impl.IDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BookingRoomHistoryOfAdminController extends ControllerBase{
    private  final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  final SimpleDateFormat inputDateFormatBirthDay = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    IBookingRoomsHistoryOfAdminService iBookingRoomsHistoryService;

    @Autowired
    IDetailServiceImpl iDetailService;

    @GetMapping("/BookingHistory")
    public ResponseEntity<?> getALl(@RequestParam(name = "page" ,defaultValue = "1") Integer page) throws  SQLException, ParseException {
        ResponseDTO<List<HistoryDTO>> result = new ResponseDTO<>();

        try {
            Page<History> histories = iBookingRoomsHistoryService.getAllHistory(page);
            List<HistoryDTO> bookingRoomsHistories = new ArrayList<>();
            for (History history :histories){
                //System.out.println(history.getRoom_Id());
                HistoryDTO roomDetailsResponseOfAdmin = getRoomDetailsResponseHistory(history);
                //System.out.println(roomDetailsResponseOfAdmin.getReserveTime());
                bookingRoomsHistories.add(roomDetailsResponseOfAdmin);
            }
            result.setData(bookingRoomsHistories);
            result.setMessage(bookingRoomsHistories.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
            return bookingRoomsHistories.isEmpty() ? notFound(result) : ok(result);
        }catch (Exception e){
            result.setMessage(e.getMessage());
            return badRequest(result);
        }
    }

    private HistoryDTO getRoomDetailsResponseHistory(History history) throws ParseException {
        String formattedReserveTime = inputDateFormat.format(history.getReserveTime());
        String formattedEndTime = inputDateFormat.format(history.getEndTime());
        String formattedReturnTime = inputDateFormat.format(history.getReturnTime());
        String acceptTime;
        if(history.getAcceptTime() ==null){
             acceptTime = "";
        }else{
             acceptTime = inputDateFormat.format(history.getAcceptTime());
        }
        return new HistoryDTO(
                history.getId(), history.getUserId(), history.getEventId(), history.getRoomId(),
                history.getFullName(), history.getRoomName(), formattedReserveTime,acceptTime,
                formattedEndTime, formattedReturnTime, history.getStatus()
        );
    }
    private int LimitPageCount(int count){

        int m  = count % 2;
        int pageCount =0;
        if(m>0){
            pageCount = count /2 + 1;
        }else{
            pageCount = count/2;
        }
        return  count;
    }


    ///Tìm kiếm
    @GetMapping("BookingHistory/Search")
    public ResponseEntity<?> searchBookingRoomHistory(
            @RequestParam(name="status", required=false) Integer status,
            @RequestParam(name="key", required=false) String key,
            @RequestParam(name="startDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(name="endDate", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page
    ) throws ParseException {
        PaginationResponseDTO<HistoryDTO> result = new PaginationResponseDTO<>();
        try {
            //Kiểm tra giá trị ngày nhận vào
            Calendar calendar = Calendar.getInstance();
            if (startDate == null || endDate == null) {
                calendar.set(2023, Calendar.JANUARY, 3, 0, 0, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startDate = calendar.getTime();

                LocalDate endDateLocalDate = LocalDate.now().plusYears(1);
                endDate = Date.from(endDateLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                //endDate = new Date();
            } else {
                startDate = resetTimeToStartOfDay(startDate);
                endDate = resetTimeToEndOfDay(endDate);
            }
            //Kiểm tra từ khóa nhận vào
            if (key != null && !key.isEmpty()) {
                Page<History> pageData = iBookingRoomsHistoryService.searchAllByStatusAndNameAndDateRange(status, key, startDate, endDate, page);
                int count = iBookingRoomsHistoryService.CountOfHistory(status,key,startDate,endDate);
                List<History> allDetails = new ArrayList<>(pageData.getContent());
                List<HistoryDTO> bookingRoomsHistories = new ArrayList<>();
                for (History detail : allDetails) {
                    HistoryDTO roomDetailsResponseOfAdmin = getRoomDetailsResponseHistory(detail);
                    bookingRoomsHistories.add(roomDetailsResponseOfAdmin);
                }
                int pageCount = LimitPageCount(count);

                result.setData(bookingRoomsHistories);
                result.setRowCount(pageCount);
                result.setPageCount(page);
                //result.setMessage(bookingRoomsHistories.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
                return bookingRoomsHistories.isEmpty() ? notFound(result) : ok(result);
            } else {
                Page<History> pageData = iBookingRoomsHistoryService.searchAllByStatusAndDateRange(status, startDate, endDate, page);

                int count = iBookingRoomsHistoryService.CountOfHistoryNotKey(status,startDate,endDate);
                List<History> allDetails = new ArrayList<>(pageData.getContent());
                List<HistoryDTO> bookingRoomsHistories = new ArrayList<>();
                for (History detail : allDetails) {
                    HistoryDTO roomDetailsResponseOfAdmin = getRoomDetailsResponseHistory(detail);
                    bookingRoomsHistories.add(roomDetailsResponseOfAdmin);
                }
                ///Kiểm tra kết quả trả về
                int pageCount = LimitPageCount(count);
                result.setData(bookingRoomsHistories);
                result.setRowCount(pageCount);
                result.setPageCount(page);
                //result.setMessage(bookingRoomsHistories.isEmpty() ? "Không tìm thấy" : "Đã tìm thấy");
                return bookingRoomsHistories.isEmpty() ? notFound(result) : ok(result);
            }
        }catch (Exception e){
            //result.setMessage(e.getMessage());
            return badRequest(result);
        }

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

    @GetMapping("/BookingHistory/Detail/{detail_id}")
    public ResponseEntity<?> getHistoryDetails(@PathVariable(name = "detail_id") int detail_id){
        ResponseDTO<HistoryDTO> result = new ResponseDTO<>();
        try {
            History history = iBookingRoomsHistoryService.getHistoryDetailsById(detail_id);
            if(history!=null){
                String formattedReserveTime = inputDateFormat.format(history.getReserveTime());
                String formattedEndTime = inputDateFormat.format(history.getEndTime());
                String formattedReturnTime = inputDateFormat.format(history.getReturnTime());
                String formatBirthDay = inputDateFormatBirthDay.format(history.getReturnTime());
                String acceptTime ;
                if(history.getAcceptTime() ==null){
                    acceptTime = "";
                }else{
                    acceptTime = inputDateFormat.format(history.getAcceptTime());
                }
                HistoryDTO bookingRoomHistoryDetails = new HistoryDTO(
                        history.getId(), history.getRoomId(),history.getUserId(), history.getEventId(), history.getRoomName(), history.getFullName(),
                        formattedReserveTime, formattedEndTime,formattedReturnTime,acceptTime, history.getStatus()
                        ,history.getDescription(),history.getPhoto(),history.getCountOfSeats(),
                        history.getTypeName(),formatBirthDay,history.getGender(),history.getEventName(),history.getArea());
                result.setData(bookingRoomHistoryDetails);
                result.setMessage("Đã tìm thấy");
                return ok(result);
            }else {
                result.setMessage("Không tìm thấy");
                return notFound(result);
            }

        } catch (Exception e) {
            result.setMessage(e.getMessage());
            return badRequest(result);
        }
    }

    @GetMapping("/BookingHistory/{detail_id}")
    public ResponseEntity<?> getHistory(@PathVariable(name = "detail_id") int id)  {
        ResponseDTO<HistoryDTO> result = new ResponseDTO<>();
        try {
            History history = iBookingRoomsHistoryService.getHistoryDetailsById(id);

            if(history!=null){
                HistoryDTO roomDetailsResponseOfAdmin = getRoomDetailsResponseHistory(history);
                result.setData(roomDetailsResponseOfAdmin);
                result.setMessage("Đã tìm thấy");
                return ok(result);
            }else {
                result.setMessage("Không tìm thấy");
                return notFound(result);
            }
        }catch (Exception e){
            result.setMessage(e.getMessage());
            return badRequest(result);
        }
    }

//    @GetMapping("/BookingHistory/Count")
//    private ResponseEntity<?> count(){
//        ResponseDTO<HistoryDTO> result = new ResponseDTO<>();
//        int count = iBookingRoomsHistoryService.CountOfHistory();
//        if(count>=0){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "Get successfully", count));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponseObject("error", "ERROR", null));
//    }


    //Xóa  ( Trong trường hợp phòng bị hủy hoặc từ chối)
    @DeleteMapping("BookingHistory/Delete/{detail_id}")
    public ResponseEntity<?> DeleteHistoryDetails(@PathVariable(name="detail_id") int id)  {
        ResponseDTO<Boolean> historyDTOResponseDTO = new ResponseDTO<>();
        try {
            History history = iBookingRoomsHistoryService.getHistoryDetailsById(id);
            if(history.getStatus()==4 || history.getStatus()==5){
                boolean result = iDetailService.deleteHistoryDetail(id);
                if(result){
                    historyDTOResponseDTO.setData(true);
                    historyDTOResponseDTO.setMessage("Xóa thành công !!!");
                    return ok(historyDTOResponseDTO);
                }else{
                    historyDTOResponseDTO.setData(false);
                    historyDTOResponseDTO.setMessage("Xóa không thành công !!!");
                    return notFound(historyDTOResponseDTO);
                }
            }
            else {
                historyDTOResponseDTO.setData(false);
                historyDTOResponseDTO.setMessage("Xóa không thành công !!!");
                return notFound(historyDTOResponseDTO);

            }
        }catch (Exception e){
            historyDTOResponseDTO.setMessage(e.getMessage());
            return badRequest(historyDTOResponseDTO);
        }

    }


//    @Transactional
//    @GetMapping("/BookingHistory/Accept/{detail_id}")
//    public ResponseEntity<?> accept(@PathVariable(name = "detail_id") int detail_id )  {
//        ResponseDTO<Boolean> historyDTOResponseDTO = new ResponseDTO<>();
//        try {
//            Details detail = iDetailService.getHistoryDetailsById(detail_id);
//            if(detail.getStatus()==1){
//                detail.setStatus(2);
//                iDetailService.save(detail);
//                List<Integer> theRoomBookingID = iDetailService.theRoomBooking(detail.getRoom().getId(),detail.getReserveTime(),detail.getEndTime());
//                System.out.println(detail.getRoom().getId());
//                for (Integer roomId : theRoomBookingID) {
//                    System.out.println(roomId);
//                    detail = iDetailService.getHistoryDetailsById(roomId);
//                    detail.setStatus(5);
//                    iDetailService.save(detail);
//                }
//                historyDTOResponseDTO.setData(true);
//                historyDTOResponseDTO.setMessage("xác nhận thành công !!!");
//                return ok(historyDTOResponseDTO);
//            }else{
//                historyDTOResponseDTO.setData(false);
//                historyDTOResponseDTO.setMessage("Xác nhận  không thành công !!!");
//                return notFound(historyDTOResponseDTO);
//            }
//
//        }catch (Exception e){
//            historyDTOResponseDTO.setData(false);
//            historyDTOResponseDTO.setMessage(e.getMessage());
//            return badRequest(historyDTOResponseDTO);
//        }
//    }
}
