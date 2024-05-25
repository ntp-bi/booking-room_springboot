package com.Booking.Booking.controller;

import com.Booking.Booking.entity.History;
import com.Booking.Booking.dto.ResponseDTO;
import com.Booking.Booking.dto.RoomDTO;
import com.Booking.Booking.dto.StatisticalOfAdminDTO;
import com.Booking.Booking.service.impl.IBookingRoomsHistoryOfAdminService;
import com.Booking.Booking.service.impl.IReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/history")
public class StatisticalController extends ControllerBase{
    private  final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private  final SimpleDateFormat inputDateFormatBirthDay = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    IBookingRoomsHistoryOfAdminService iBookingRoomsHistoryService;
    @Autowired
    private IReportService reportService;



    ///Thống kế theo năm
    @GetMapping("/StatisticalByYear/{year}")
    private ResponseEntity<?> statisticalByYear(@PathVariable(name="year")Integer year){
        ResponseDTO<StatisticalOfAdminDTO> result = new ResponseDTO<StatisticalOfAdminDTO>();

        StatisticalOfAdminDTO statisticalOfAdmin =  new StatisticalOfAdminDTO();

        statisticalOfAdmin.setDay(0);
        statisticalOfAdmin.setMonth(0);
        statisticalOfAdmin.setYear(year);
        int countOfEvent = 0;
        ///Số sự kiện được tổ chức trong năm (Tính tổng theo id sự kiện)
        List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByYear(year);
        for (Integer id : listOfEventByDay){
            countOfEvent++;
        }
        ///Các tổng các sự kiện được tổ chức trong năm
        //countOfEvent = iBookingRoomsHistoryService.countOfEventByYear(year);

        statisticalOfAdmin.setCountOfEvent(countOfEvent);
        //Số phòng được đặt trong năm ( Tính tổng theo id phòng)
        List<Integer> list = iBookingRoomsHistoryService.getRoomID(year);
        int countOfRoom =0 ;
        for (Integer id : list){
            countOfRoom++;
        }
        ///Tổng số phòng được đặt trong năm
        //countOfRoom = iBookingRoomsHistoryService.countOfRoom(year);

        statisticalOfAdmin.setCountOfRoomType(countOfRoom);
        ///Loại phòng được đặt nhiều nhất
        List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByYear(year);
        List<RoomDTO> roomResponses = new ArrayList<>();
        for (Integer roomId : theMostRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History history = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(history.getRoomId());
            roomResponse.setRoomName(history.getRoomName());
            roomResponse.setArea(history.getArea());
            roomResponse.setImage(history.getPhoto());
            roomResponse.setStatus(history.getStatus());
            roomResponse.setDescription(history.getDescription());
            roomResponse.setCountOfSeats(history.getCountOfSeats());
            roomResponse.setTypeName(history.getTypeName());
            roomResponses.add(roomResponse);
        }
        statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
        ///Số lần đặt của loại phòng được đặt nhiều nhất
        statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByYear(year));
        ///Loại phòng được đặt ít nhất
        List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByYear(year);
        List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
        for (Integer roomId : theLeastRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History room = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(room.getRoomId());
            roomResponse.setRoomName(room.getRoomName());
            roomResponse.setArea(room.getArea());
            roomResponse.setImage(room.getPhoto());
            roomResponse.setStatus(room.getStatus());
            roomResponse.setDescription(room.getDescription());
            roomResponse.setTypeName(room.getTypeName());
            roomResponse.setCountOfSeats(room.getCountOfSeats());
            RoomTypeNamesOfLeast.add(roomResponse);
        }
        statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
        ///Số lần đặt của loại phòng được đặt ít nhất
        statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByYear(year));
//        ///Số lượng giáo viên trong trường đặt phòng
//
        int counOfTeacher = 0;
        List<Integer> list1 = iBookingRoomsHistoryService.getTeacherByYear(year);
        for (Integer id : list1){
            counOfTeacher++;
        }
        statisticalOfAdmin.setCountOfTeacher(counOfTeacher);
        ///Số lượng phòng được đặt trong năm
        statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByYear(year));
//        ///Số lượng phòng hủy đặt trong năm
        statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByYear(year));


        result.setData(statisticalOfAdmin);
        result.setMessage("Thống kê thành công");
        return ok(result);
    }

    ///Thống kê theo tháng
    @GetMapping("/StatisticalByMonth")
    private ResponseEntity<?> statisticalByMonth( @RequestParam(name="year")Integer year , @RequestParam(name ="month") Integer month){
        ResponseDTO<StatisticalOfAdminDTO> result = new ResponseDTO<>();
        StatisticalOfAdminDTO statisticalOfAdmin =  new StatisticalOfAdminDTO();
        statisticalOfAdmin.setDay(0);
        statisticalOfAdmin.setMonth(month);
        statisticalOfAdmin.setYear(year);
        ///Số sự kiện được tổ chức
        int countOfEvent = 0;
        List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByMonth(month,year);
        for (Integer id : listOfEventByDay){
            countOfEvent++;
        }
        statisticalOfAdmin.setCountOfEvent(countOfEvent);
        //Số loại phòng
        List<Integer> listOfRoomTypeID = iBookingRoomsHistoryService.getRoomTypeByMonth(month,year);
        int count = 0;
        for (Integer id : listOfRoomTypeID){
            count++;
        }
        statisticalOfAdmin.setCountOfRoomType(count);
        ///Loại phòng được đặt nhiều nhất
        List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByMonth(month,year);

        List<RoomDTO> roomResponses = new ArrayList<>();
        for (Integer roomId : theMostRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History room = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(room.getRoomId());
            roomResponse.setRoomName(room.getRoomName());
            roomResponse.setArea(room.getArea());
            roomResponse.setImage(room.getPhoto());
            roomResponse.setCountOfSeats(room.getCountOfSeats());
            roomResponse.setStatus(room.getStatus());
            roomResponse.setDescription(room.getDescription());
            roomResponse.setTypeName(room.getTypeName());
            roomResponses.add(roomResponse);
        }
        statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
        ///Số lần đặt của loại phòng được đặt nhiều nhất
        statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByMonth(month,year));
        ///Loại phòng được đặt ít nhất
        List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByMonth(month,year);
        List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
        for (Integer roomId : theLeastRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History room = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(room.getRoomId());
            roomResponse.setRoomName(room.getRoomName());
            roomResponse.setArea(room.getArea());
            roomResponse.setImage(room.getPhoto());
            roomResponse.setCountOfSeats(room.getCountOfSeats());
            roomResponse.setStatus(room.getStatus());
            roomResponse.setDescription(room.getDescription());
            roomResponse.setTypeName(room.getTypeName());
            RoomTypeNamesOfLeast.add(roomResponse);
        }
        statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
        ///Số lần đặt của loại phòng được đặt ít nhất
        statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByMonth(month,year));
        ///Số lượng giáo viên trong trường đã đặt phòng
        List<Integer> listOfUserID = iBookingRoomsHistoryService.getUserIdByMonth(month,year);

        int countOfUserId = 0;
        for (Integer id : listOfUserID){
            countOfUserId++;
        }

        statisticalOfAdmin.setCountOfTeacher(countOfUserId);
        ///Số lượng phòng được đặt trong năm
        statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByMonth(month,year));
        ///Số lượng phòng hủy đặt trong năm
        statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByMonth(month,year));
        result.setData(statisticalOfAdmin);
        result.setMessage("Thống kê thành công");
        return ok(result);
    }

    ///Thống kê theo ngày
    @GetMapping("/StatisticalByDay")
    private ResponseEntity<?> statisticalByDay( @RequestParam(name="year")Integer year , @RequestParam(name ="month") Integer month ,@RequestParam(name="day") Integer day){
        ResponseDTO<StatisticalOfAdminDTO> result = new ResponseDTO<>();
        StatisticalOfAdminDTO statisticalOfAdmin =  new StatisticalOfAdminDTO();
        statisticalOfAdmin.setDay(day);
        statisticalOfAdmin.setMonth(month);
        statisticalOfAdmin.setYear(year);
        ///Số sự kiện được tổ chức trong năm
        int countOfEvent = 0;
        List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByDay(day,month,year);
        for (Integer id : listOfEventByDay){
            countOfEvent++;
        }
        statisticalOfAdmin.setCountOfEvent(countOfEvent);
        //Số loại phòng
        List<Integer> listOfRoomType = iBookingRoomsHistoryService.getRoomTypeByDay(day,month,year);
        int count = 0 ;
        for (Integer id : listOfRoomType){
            count++;
        }
        statisticalOfAdmin.setCountOfRoomType(count);
        ///Loại phòng được đặt nhiều nhất
        List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByDay(day,month,year);

        List<RoomDTO> roomResponses = new ArrayList<>();
        for (Integer roomId : theMostRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History room = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(room.getRoomId());
            roomResponse.setRoomName(room.getRoomName());
            roomResponse.setArea(room.getArea());
            roomResponse.setImage(room.getPhoto());
            roomResponse.setStatus(room.getStatus());
            roomResponse.setDescription(room.getDescription());
            roomResponse.setCountOfSeats(room.getCountOfSeats());
            roomResponse.setTypeName(room.getTypeName());
            roomResponses.add(roomResponse);
        }
        statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
        ///Số lần đặt của loại phòng được đặt nhiều nhất
        statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByDay(day,month,year));
        ///Loại phòng được đặt ít nhất
        List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByDay(day,month,year);
        List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
        for (Integer roomId : theLeastRoomTypeBookingID) {
            RoomDTO roomResponse = new RoomDTO();
            History room = iBookingRoomsHistoryService.getRoomById(roomId);
            roomResponse.setId(room.getRoomId());
            roomResponse.setRoomName(room.getRoomName());
            roomResponse.setArea(room.getArea());
            roomResponse.setImage(room.getPhoto());
            roomResponse.setStatus(room.getStatus());
            roomResponse.setDescription(room.getDescription());
            roomResponse.setTypeName(room.getTypeName());
            roomResponse.setCountOfSeats(room.getCountOfSeats());
            RoomTypeNamesOfLeast.add(roomResponse);
        }
        statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
        ///Số lần đặt của loại phòng được đặt ít nhất
        statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByDay(day,month,year));
        ///Số lượng giáo viên trong trường
        int countOfTeacher = 0;
        List<Integer> listOfTeacher = iBookingRoomsHistoryService.getTeacherByDay(day,month,year);
        for (Integer id : listOfTeacher){
            countOfTeacher++;
        }

        statisticalOfAdmin.setCountOfTeacher(countOfTeacher);
        ///Số lượng phòng được đặt trong năm
        statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByDay(day,month,year));
        ///Số lượng phòng hủy đặt trong năm
        statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByDay(day,month,year));
        result.setData(statisticalOfAdmin);
        result.setMessage("Thống kê thành công");
        return ok(result);
    }

    @GetMapping("/Statistical")
    private ResponseEntity<?> statistical( @RequestParam(name="year")Integer year , @RequestParam(name ="month",required = false) Integer month ,@RequestParam(name="day",required = false) Integer day){
        ResponseDTO<StatisticalOfAdminDTO> result = new ResponseDTO<>();
        StatisticalOfAdminDTO statisticalOfAdmin =  new StatisticalOfAdminDTO();
        if(day ==null && month !=null && year !=null){
            statisticalOfAdmin.setDay(0);
            statisticalOfAdmin.setMonth(month);
            statisticalOfAdmin.setYear(year);
            ///Số sự kiện được tổ chức
            int countOfEvent = 0;
            List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByMonth(month,year);
            for (Integer id : listOfEventByDay){
                countOfEvent++;
            }
            statisticalOfAdmin.setCountOfEvent(countOfEvent);
            //Số loại phòng
            List<Integer> listOfRoomTypeID = iBookingRoomsHistoryService.getRoomTypeByMonth(month,year);
            int count = 0;
            for (Integer id : listOfRoomTypeID){
                count++;
            }
            statisticalOfAdmin.setCountOfRoomType(count);
            ///Loại phòng được đặt nhiều nhất
            List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByMonth(month,year);

            List<RoomDTO> roomResponses = new ArrayList<>();
            for (Integer roomId : theMostRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setTypeName(room.getTypeName());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                roomResponses.add(roomResponse);
            }
            statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
            ///Số lần đặt của loại phòng được đặt nhiều nhất
            statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByMonth(month,year));
            ///Loại phòng được đặt ít nhất
            List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByMonth(month,year);
            List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
            for (Integer roomId : theLeastRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setTypeName(room.getTypeName());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                RoomTypeNamesOfLeast.add(roomResponse);
            }
            statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
            ///Số lần đặt của loại phòng được đặt ít nhất
            statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByMonth(month,year));
            ///Số lượng giáo viên trong trường đã đặt phòng
            List<Integer> listOfUserID = iBookingRoomsHistoryService.getUserIdByMonth(month,year);

            int countOfUserId = 0;
            for (Integer id : listOfUserID){
                countOfUserId++;
            }

            statisticalOfAdmin.setCountOfTeacher(countOfUserId);
            ///Số lượng phòng được đặt trong năm
            statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByMonth(month,year));
            ///Số lượng phòng hủy đặt trong năm
            statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByMonth(month,year));
            result.setData(statisticalOfAdmin);
            result.setMessage("Thống kê thành công");
            return ok(result);
        }
        if(day ==null && month ==null && year !=null){
            statisticalOfAdmin.setDay(0);
            statisticalOfAdmin.setMonth(0);
            statisticalOfAdmin.setYear(year);
            int countOfEvent = 0;
            List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByYear(year);
            for (Integer id : listOfEventByDay){
                countOfEvent++;
            }
            statisticalOfAdmin.setCountOfEvent(countOfEvent);
            //Số loại phòng
            List<Integer> list = iBookingRoomsHistoryService.getRoomID(year);
            int sum =0 ;
            for (Integer id : list){
                sum++;
            }
            statisticalOfAdmin.setCountOfRoomType(sum);
            ///Loại phòng được đặt nhiều nhất
            List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByYear(year);
            //RoomDto roomResponse = new RoomDto();
            List<RoomDTO> roomResponses = new ArrayList<>();
            for (Integer roomId : theMostRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                roomResponse.setTypeName(room.getTypeName());
                roomResponses.add(roomResponse);
            }

            statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
            ///Số lần đặt của loại phòng được đặt nhiều nhất
            statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByYear(year));
            ///Loại phòng được đặt ít nhất
            List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByYear(year);
            List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
            for (Integer roomId : theLeastRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                roomResponse.setTypeName(room.getTypeName());
                RoomTypeNamesOfLeast.add(roomResponse);
            }
            statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
            ///Số lần đặt của loại phòng được đặt ít nhất
            statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByYear(year));
            ///Số lượng giáo viên trong trường đặt phòng

            int counOfTeacher = 0;
            List<Integer> list1 = iBookingRoomsHistoryService.getTeacherByYear(year);
            for (Integer id : list1){
                counOfTeacher++;
            }
            statisticalOfAdmin.setCountOfTeacher(counOfTeacher);
            ///Số lượng phòng được đặt trong năm
            statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByYear(year));
            ///Số lượng phòng hủy đặt trong năm
            statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByYear(year));
            result.setData(statisticalOfAdmin);
            result.setMessage("Thống kê thành công");
            return ok(result);
        }
        if(day !=null && month !=null && year !=null){
            statisticalOfAdmin.setDay(day);
            statisticalOfAdmin.setMonth(month);
            statisticalOfAdmin.setYear(year);
            ///Số sự kiện được tổ chức trong năm
            int countOfEvent = 0;
            List<Integer> listOfEventByDay =iBookingRoomsHistoryService.getEvenByDay(day,month,year);
            for (Integer id : listOfEventByDay){
                countOfEvent++;
            }
            statisticalOfAdmin.setCountOfEvent(countOfEvent);
            //Số loại phòng
            List<Integer> listOfRoomType = iBookingRoomsHistoryService.getRoomTypeByDay(day,month,year);
            int count = 0 ;
            for (Integer id : listOfRoomType){
                count++;
            }
            statisticalOfAdmin.setCountOfRoomType(count);
            ///Loại phòng được đặt nhiều nhất
            List<Integer> theMostRoomTypeBookingID = iBookingRoomsHistoryService.theMostRoomTypeBookingIDByDay(day,month,year);

            List<RoomDTO> roomResponses = new ArrayList<>();
            for (Integer roomId : theMostRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setTypeName(room.getTypeName());
                roomResponses.add(roomResponse);
            }
            statisticalOfAdmin.setTheMostRoomTypeOfBooking(roomResponses);
            ///Số lần đặt của loại phòng được đặt nhiều nhất
            statisticalOfAdmin.setTheMostCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheMostCountOfRoomTypeOfBookingByDay(day,month,year));
            ///Loại phòng được đặt ít nhất
            List<Integer> theLeastRoomTypeBookingID = iBookingRoomsHistoryService.theLeastRoomTypeBookingIdByDay(day,month,year);
            List<RoomDTO> RoomTypeNamesOfLeast = new ArrayList<>();
            for (Integer roomId : theLeastRoomTypeBookingID) {
                RoomDTO roomResponse = new RoomDTO();
                History room = iBookingRoomsHistoryService.getRoomById(roomId);
                roomResponse.setId(room.getRoomId());
                roomResponse.setRoomName(room.getRoomName());
                roomResponse.setArea(room.getArea());
                roomResponse.setImage(room.getPhoto());
                roomResponse.setStatus(room.getStatus());
                roomResponse.setCountOfSeats(room.getCountOfSeats());
                roomResponse.setDescription(room.getDescription());
                roomResponse.setTypeName(room.getTypeName());
                RoomTypeNamesOfLeast.add(roomResponse);
            }
            statisticalOfAdmin.setLeastOfRoomTypeOfBooking(RoomTypeNamesOfLeast);
            ///Số lần đặt của loại phòng được đặt ít nhất
            statisticalOfAdmin.setTheLeastCountOfRoomTypeOfBooking(iBookingRoomsHistoryService.TheLeastCountOfRoomTypeOfBookingByDay(day,month,year));
            ///Số lượng giáo viên trong trường
            int countOfTeacher = 0;
            List<Integer> listOfTeacher = iBookingRoomsHistoryService.getTeacherByDay(day,month,year);
            for (Integer id : listOfTeacher){
                countOfTeacher++;
            }

            statisticalOfAdmin.setCountOfTeacher(countOfTeacher);
            ///Số lượng phòng được đặt trong năm
            statisticalOfAdmin.setCountOfBookingRoom(iBookingRoomsHistoryService.CountOfBookingRoomByDay(day,month,year));
            ///Số lượng phòng hủy đặt trong năm
            statisticalOfAdmin.setCountOfReturnBookingRoom(iBookingRoomsHistoryService.CountOfReturnBookingRoomByDay(day,month,year));
            result.setData(statisticalOfAdmin);
            result.setMessage("Thống kê thành công");
            return ok(result);
        }

        result.setMessage("Thống kê không thành công");
        return notFound(result);
    }

    @GetMapping("/StatisticalByYear/excel/{year}")
    private void generateExcelReportByYear(@PathVariable(name = "year") int year, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=StatisticalByYear.xls";

        response.setHeader(headerKey,headerValue);
        reportService.generateExcel(response,year);
    }



    @GetMapping("/StatisticalByYear/excel/{year}/{month}")
    private void generateExcelReportByMonth(@PathVariable(name = "year") int year,
                                            @PathVariable(name = "month") int month,
                                            HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=StatisticalByMonth.xls";

        response.setHeader(headerKey,headerValue);
        reportService.generateExcelByMonth(response,year,month);
    }

    @GetMapping("/StatisticalByYear/excel/{year}/{month}/{day}")
    private void generateExcelReportByDay(@PathVariable(name = "year") int year,
                                          @PathVariable(name = "month") int month,
                                          @PathVariable(name = "day") int day,
                                          HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=StatisticalByDay.xls";

        response.setHeader(headerKey,headerValue);
        reportService.generateExcelByDay(response,year,month,day);
    }


    @GetMapping("/Statistical/excel/all")
    private void generateExcelReport(@RequestParam(name = "year",required = false) Integer year,
                                     @RequestParam(name = "month",required = false) Integer month,
                                     @RequestParam(name = "day",required = false) Integer day,
                                     HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        if(day ==null && month ==null && year != null){
            String headerKey = "Content-Disposition";
            String headerValue = "attachment;filename=StatisticalByYear.xls";
            response.setHeader(headerKey,headerValue);
            reportService.generateExcel(response,year);
        }else if(day == null && month!=null && year !=null){
            String headerKey = "Content-Disposition";
            String headerValue = "attachment;filename=StatisticalByMonth.xls";
            response.setHeader(headerKey,headerValue);
            reportService.generateExcelByMonth(response,year,month);
        }else{
            String headerKey = "Content-Disposition";
            String headerValue = "attachment;filename=StatisticalByDay.xls";
            response.setHeader(headerKey,headerValue);
            reportService.generateExcelByDay(response,year,month,day);
        }

    }

}
