package com.Booking.Booking.service.impl;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IReportService {
    void generateExcel(HttpServletResponse response , int year) throws IOException;
    void generateExcelByMonth(HttpServletResponse response ,int year,int month) throws IOException;
    void generateExcelByDay(HttpServletResponse response ,int year,int month, int day) throws IOException;
}
