package com.Booking.Booking.service;

import com.Booking.Booking.entity.History;
import com.Booking.Booking.service.impl.IBookingRoomsHistoryOfAdminService;
import com.Booking.Booking.service.impl.IReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImp implements IReportService {

    @Autowired
    public IBookingRoomsHistoryOfAdminService historyOfAdminRepository;
    public void generateExcel(HttpServletResponse response, int year) throws IOException {
        List<Integer> countOfRoomTypes = historyOfAdminRepository.getRoomID(year);
        int countOfRoomType = countOfRoomTypes.size();

        int MostCountOfRoomTypeOfBooking = historyOfAdminRepository.TheMostCountOfRoomTypeOfBookingByYear(year);
        int countOfTheLeastRoomOfBooking = historyOfAdminRepository.TheLeastCountOfRoomTypeOfBookingByYear(year);

        List<Integer> countOfEvents = historyOfAdminRepository.getEvenByYear(year);
        int countOfEvent = countOfEvents.size();

        List<Integer> countOfTeachers = historyOfAdminRepository.getTeacherByYear(year);
        int countOfTeacher = countOfTeachers.size();

        int countOfReturnBookingRoom = historyOfAdminRepository.CountOfReturnBookingRoomByYear(year);
        int countOfBookingRoom = historyOfAdminRepository.CountOfBookingRoomByYear(year);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("StatisticalByYear");

        // Tạo đối tượng font cho tiêu đề chính
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Arial"); // Đặt tên phông chữ
        titleFont.setFontHeightInPoints((short) 30); // Đặt kích thước phông chữ
        titleFont.setBold(true); // Đặt phông chữ đậm

        // Tạo đối tượng cell style và gán phông chữ vào style cho tiêu đề chính
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        // Áp dụng style cho ô tiêu đề chính
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(6);
        cellTitle.setCellValue("Thống kê năm " + year);
        cellTitle.setCellStyle(titleStyle);

        // Tạo đối tượng font cho các chữ khác
        HSSFFont otherFont = workbook.createFont();
        otherFont.setFontName("Arial");
        otherFont.setFontHeightInPoints((short) 16);
        otherFont.setBold(true);


        HSSFFont otherFont1 = workbook.createFont();
        otherFont1.setFontName("Arial");
        otherFont1.setFontHeightInPoints((short) 16);
        otherFont1.setBold(false);


        HSSFCellStyle otherStyle1 = workbook.createCellStyle();
        otherStyle1.setFont(otherFont1);




        // Tạo đối tượng cell style và gán phông chữ vào style cho các chữ khác
        HSSFCellStyle otherStyle = workbook.createCellStyle();
        otherStyle.setFont(otherFont);

        // Tạo đối tượng font cho các giá trị
        HSSFFont valueFont = workbook.createFont();
        valueFont.setFontName("Arial");
        valueFont.setFontHeightInPoints((short) 13);
        valueFont.setBold(false);

        // Tạo đối tượng cell style và gán phông chữ vào style cho các giá trị
        HSSFCellStyle valueStyle = workbook.createCellStyle();
        valueStyle.setFont(valueFont);

        // Tạo các dòng và ô khác và áp dụng style
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(1);
        cell.setCellValue("Số lượng loại phòng được đặt");
        cell.setCellStyle(otherStyle);
        HSSFCell valueCell = row.createCell(2);
        valueCell.setCellValue(countOfRoomType);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row1 = sheet.createRow(3);
        cell = row1.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row1.createCell(2);
        valueCell.setCellValue(MostCountOfRoomTypeOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row2 = sheet.createRow(4);
        cell = row2.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row2.createCell(2);
        valueCell.setCellValue(countOfTheLeastRoomOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row3 = sheet.createRow(5);
        cell = row3.createCell(1);
        cell.setCellValue("Số lượng sự kiện");
        cell.setCellStyle(otherStyle);
        valueCell = row3.createCell(2);
        valueCell.setCellValue(countOfEvent);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row4 = sheet.createRow(6);
        cell = row4.createCell(1);
        cell.setCellValue("Số lượng giáo viên đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row4.createCell(2);
        valueCell.setCellValue(countOfTeacher);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row5 = sheet.createRow(7);
        cell = row5.createCell(1);
        cell.setCellValue("Số lượt hủy phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row5.createCell(2);
        valueCell.setCellValue(countOfReturnBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row10 = sheet.createRow(8);
        cell = row10.createCell(1);
        cell.setCellValue("Số lượt đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row10.createCell(2);
        valueCell.setCellValue(countOfBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row6 = sheet.createRow(10);
        cell = row6.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row7 = sheet.createRow(11);
        cell = row7.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        int dataRowIndex = 12;
        List<Integer> theLeastRoomTypeBookingID = historyOfAdminRepository.theMostRoomTypeBookingIDByYear(year);
        for (Integer roomId : theLeastRoomTypeBookingID) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        HSSFRow row8 = sheet.createRow(dataRowIndex + 2);
        cell = row8.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row9 = sheet.createRow(dataRowIndex + 3);
        cell = row9.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        dataRowIndex = dataRowIndex + 4;
        List<Integer> theMostRoomTypeBookingIDByYear = historyOfAdminRepository.theLeastRoomTypeBookingIdByYear(year);
        for (Integer roomId : theMostRoomTypeBookingIDByYear) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        // Tự động điều chỉnh độ rộng các cột
        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateExcelByMonth(HttpServletResponse response, int year, int month) throws IOException {
        List<Integer> countOfRoomTypes = historyOfAdminRepository.getRoomTypeByMonth(month,year);
        int countOfRoomType = countOfRoomTypes.size();

        int MostCountOfRoomTypeOfBooking = historyOfAdminRepository.TheMostCountOfRoomTypeOfBookingByMonth(month,year);
        int countOfTheLeastRoomOfBooking = historyOfAdminRepository.TheLeastCountOfRoomTypeOfBookingByMonth(month,year);

        List<Integer> countOfEvents = historyOfAdminRepository.getEvenByMonth(month,year);
        int countOfEvent = countOfEvents.size();

        List<Integer> countOfTeachers = historyOfAdminRepository.getUserIdByMonth(month,year);
        int countOfTeacher = countOfTeachers.size();

        int countOfReturnBookingRoom = historyOfAdminRepository.CountOfReturnBookingRoomByMonth(month,year);
        int countOfBookingRoom = historyOfAdminRepository.CountOfBookingRoomByMonth(month,year);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("StatisticalByMonth");

        // Tạo đối tượng font cho tiêu đề chính
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Arial"); // Đặt tên phông chữ
        titleFont.setFontHeightInPoints((short) 30); // Đặt kích thước phông chữ
        titleFont.setBold(true); // Đặt phông chữ đậm

        // Tạo đối tượng cell style và gán phông chữ vào style cho tiêu đề chính
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        // Áp dụng style cho ô tiêu đề chính
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(6);
        cellTitle.setCellValue("Thống kê tháng " + month + " năm" + year);
        cellTitle.setCellStyle(titleStyle);

        // Tạo đối tượng font cho các chữ khác
        HSSFFont otherFont = workbook.createFont();
        otherFont.setFontName("Arial");
        otherFont.setFontHeightInPoints((short) 16);
        otherFont.setBold(true);


        HSSFFont otherFont1 = workbook.createFont();
        otherFont1.setFontName("Arial");
        otherFont1.setFontHeightInPoints((short) 16);
        otherFont1.setBold(false);


        HSSFCellStyle otherStyle1 = workbook.createCellStyle();
        otherStyle1.setFont(otherFont1);




        // Tạo đối tượng cell style và gán phông chữ vào style cho các chữ khác
        HSSFCellStyle otherStyle = workbook.createCellStyle();
        otherStyle.setFont(otherFont);

        // Tạo đối tượng font cho các giá trị
        HSSFFont valueFont = workbook.createFont();
        valueFont.setFontName("Arial");
        valueFont.setFontHeightInPoints((short) 13);
        valueFont.setBold(false);

        // Tạo đối tượng cell style và gán phông chữ vào style cho các giá trị
        HSSFCellStyle valueStyle = workbook.createCellStyle();
        valueStyle.setFont(valueFont);

        // Tạo các dòng và ô khác và áp dụng style
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(1);
        cell.setCellValue("Số lượng loại phòng được đặt");
        cell.setCellStyle(otherStyle);
        HSSFCell valueCell = row.createCell(2);
        valueCell.setCellValue(countOfRoomType);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row1 = sheet.createRow(3);
        cell = row1.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row1.createCell(2);
        valueCell.setCellValue(MostCountOfRoomTypeOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row2 = sheet.createRow(4);
        cell = row2.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row2.createCell(2);
        valueCell.setCellValue(countOfTheLeastRoomOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row3 = sheet.createRow(5);
        cell = row3.createCell(1);
        cell.setCellValue("Số lượng sự kiện");
        cell.setCellStyle(otherStyle);
        valueCell = row3.createCell(2);
        valueCell.setCellValue(countOfEvent);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row4 = sheet.createRow(6);
        cell = row4.createCell(1);
        cell.setCellValue("Số lượng giáo viên đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row4.createCell(2);
        valueCell.setCellValue(countOfTeacher);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row5 = sheet.createRow(7);
        cell = row5.createCell(1);
        cell.setCellValue("Số lượt hủy phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row5.createCell(2);
        valueCell.setCellValue(countOfReturnBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row10 = sheet.createRow(8);
        cell = row10.createCell(1);
        cell.setCellValue("Số lượt đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row10.createCell(2);
        valueCell.setCellValue(countOfBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row6 = sheet.createRow(10);
        cell = row6.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row7 = sheet.createRow(11);
        cell = row7.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        int dataRowIndex = 12;
        List<Integer> theLeastRoomTypeBookingID = historyOfAdminRepository.theMostRoomTypeBookingIDByMonth(month,year);
        for (Integer roomId : theLeastRoomTypeBookingID) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        HSSFRow row8 = sheet.createRow(dataRowIndex + 2);
        cell = row8.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row9 = sheet.createRow(dataRowIndex + 3);
        cell = row9.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        dataRowIndex = dataRowIndex + 4;
        List<Integer> theMostRoomTypeBookingIDByYear = historyOfAdminRepository.theLeastRoomTypeBookingIdByMonth(month,year);
        for (Integer roomId : theMostRoomTypeBookingIDByYear) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        // Tự động điều chỉnh độ rộng các cột
        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateExcelByDay(HttpServletResponse response, int year, int month, int day) throws IOException {
        List<Integer> countOfRoomTypes = historyOfAdminRepository.getRoomTypeByDay(day,month,year);
        int countOfRoomType = countOfRoomTypes.size();

        int MostCountOfRoomTypeOfBooking = historyOfAdminRepository.TheMostCountOfRoomTypeOfBookingByDay(day,month,year);
        int countOfTheLeastRoomOfBooking = historyOfAdminRepository.TheLeastCountOfRoomTypeOfBookingByDay(day,month,year);

        List<Integer> countOfEvents = historyOfAdminRepository.getEvenByDay(day,month,year);
        int countOfEvent = countOfEvents.size();

        List<Integer> countOfTeachers = historyOfAdminRepository.getTeacherByDay(day,month,year);
        int countOfTeacher = countOfTeachers.size();

        int countOfReturnBookingRoom = historyOfAdminRepository.CountOfReturnBookingRoomByDay(day,month,year);
        int countOfBookingRoom = historyOfAdminRepository.CountOfBookingRoomByDay(day,month,year);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("StatisticalByMonth");

        // Tạo đối tượng font cho tiêu đề chính
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Arial"); // Đặt tên phông chữ
        titleFont.setFontHeightInPoints((short) 30); // Đặt kích thước phông chữ
        titleFont.setBold(true); // Đặt phông chữ đậm

        // Tạo đối tượng cell style và gán phông chữ vào style cho tiêu đề chính
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);

        // Áp dụng style cho ô tiêu đề chính
        HSSFRow rowTitle = sheet.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(6);
        cellTitle.setCellValue("Thống kê ngày " + day +   " tháng " + month + " năm" + year);
        cellTitle.setCellStyle(titleStyle);

        // Tạo đối tượng font cho các chữ khác
        HSSFFont otherFont = workbook.createFont();
        otherFont.setFontName("Arial");
        otherFont.setFontHeightInPoints((short) 16);
        otherFont.setBold(true);


        HSSFFont otherFont1 = workbook.createFont();
        otherFont1.setFontName("Arial");
        otherFont1.setFontHeightInPoints((short) 16);
        otherFont1.setBold(false);


        HSSFCellStyle otherStyle1 = workbook.createCellStyle();
        otherStyle1.setFont(otherFont1);




        // Tạo đối tượng cell style và gán phông chữ vào style cho các chữ khác
        HSSFCellStyle otherStyle = workbook.createCellStyle();
        otherStyle.setFont(otherFont);

        // Tạo đối tượng font cho các giá trị
        HSSFFont valueFont = workbook.createFont();
        valueFont.setFontName("Arial");
        valueFont.setFontHeightInPoints((short) 13);
        valueFont.setBold(false);

        // Tạo đối tượng cell style và gán phông chữ vào style cho các giá trị
        HSSFCellStyle valueStyle = workbook.createCellStyle();
        valueStyle.setFont(valueFont);

        // Tạo các dòng và ô khác và áp dụng style
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(1);
        cell.setCellValue("Số lượng loại phòng được đặt");
        cell.setCellStyle(otherStyle);
        HSSFCell valueCell = row.createCell(2);
        valueCell.setCellValue(countOfRoomType);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row1 = sheet.createRow(3);
        cell = row1.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row1.createCell(2);
        valueCell.setCellValue(MostCountOfRoomTypeOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row2 = sheet.createRow(4);
        cell = row2.createCell(1);
        cell.setCellValue("Số lượng phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);
        valueCell = row2.createCell(2);
        valueCell.setCellValue(countOfTheLeastRoomOfBooking);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row3 = sheet.createRow(5);
        cell = row3.createCell(1);
        cell.setCellValue("Số lượng sự kiện");
        cell.setCellStyle(otherStyle);
        valueCell = row3.createCell(2);
        valueCell.setCellValue(countOfEvent);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row4 = sheet.createRow(6);
        cell = row4.createCell(1);
        cell.setCellValue("Số lượng giáo viên đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row4.createCell(2);
        valueCell.setCellValue(countOfTeacher);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row5 = sheet.createRow(7);
        cell = row5.createCell(1);
        cell.setCellValue("Số lượt hủy phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row5.createCell(2);
        valueCell.setCellValue(countOfReturnBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row10 = sheet.createRow(8);
        cell = row10.createCell(1);
        cell.setCellValue("Số lượt đặt phòng");
        cell.setCellStyle(otherStyle);
        valueCell = row10.createCell(2);
        valueCell.setCellValue(countOfBookingRoom);
        valueCell.setCellStyle(valueStyle);

        HSSFRow row6 = sheet.createRow(10);
        cell = row6.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt nhiều nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row7 = sheet.createRow(11);
        cell = row7.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row7.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        int dataRowIndex = 12;
        List<Integer> theLeastRoomTypeBookingID = historyOfAdminRepository.theMostRoomTypeBookingIDByDay(day,month,year);
        for (Integer roomId : theLeastRoomTypeBookingID) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        HSSFRow row8 = sheet.createRow(dataRowIndex + 2);
        cell = row8.createCell(1);
        cell.setCellValue("Danh sách các phòng được đặt ít nhất");
        cell.setCellStyle(otherStyle);

        HSSFRow row9 = sheet.createRow(dataRowIndex + 3);
        cell = row9.createCell(2);
        cell.setCellValue("ID ");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(3);
        cell.setCellValue("Tên phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(4);
        cell.setCellValue("Tên loại phòng");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(5);
        cell.setCellValue("Số lượng chỗ ngồi");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(6);
        cell.setCellValue("Mô tả");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(7);
        cell.setCellValue("Ảnh");
        cell.setCellStyle(otherStyle1);
        cell = row9.createCell(8);
        cell.setCellValue("Diện tích");
        cell.setCellStyle(otherStyle1);

        dataRowIndex = dataRowIndex + 4;
        List<Integer> theMostRoomTypeBookingIDByYear = historyOfAdminRepository.theLeastRoomTypeBookingIdByDay(day,month,year);
        for (Integer roomId : theMostRoomTypeBookingIDByYear) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            History history1 = historyOfAdminRepository.findByRoomId(roomId);
            HSSFCell dataCell = dataRow.createCell(2);
            dataCell.setCellValue(history1.getRoomId());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(3);
            dataCell.setCellValue(history1.getRoomName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(4);
            dataCell.setCellValue(history1.getTypeName());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(5);
            dataCell.setCellValue(history1.getCountOfSeats());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(6);
            dataCell.setCellValue(history1.getDescription());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(7);
            dataCell.setCellValue(history1.getPhoto());
            dataCell.setCellStyle(valueStyle);
            dataCell = dataRow.createCell(8);
            dataCell.setCellValue(history1.getArea());
            dataCell.setCellStyle(valueStyle);
            dataRowIndex++;
        }

        // Tự động điều chỉnh độ rộng các cột
        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}
