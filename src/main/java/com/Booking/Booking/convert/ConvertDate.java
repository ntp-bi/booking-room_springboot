package com.Booking.Booking.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ConvertDate {
	public String[] formatDatetoString(Date d1, Date d2, Date d3) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reservetimeValue = formatDate.format(d1);
        String endtimeValue = formatDate.format(d2);
        String returntimeValue = formatDate.format(d3);
        
        // Trả về một mảng chứa các giá trị đã định dạng
        return new String[] { reservetimeValue, endtimeValue, returntimeValue };
    }
	
	public Date[] parseStringtoDate(String d1, String d2, String d3) throws Exception {
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date reservetimeValue = formatDate.parse(d1);
			Date endtimeValue = formatDate.parse(d2);
			Date returntimeValue = formatDate.parse(d3);
			return new Date[] {reservetimeValue, endtimeValue, returntimeValue};
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public String formatDatetoString(Date d1) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String acceptValue = formatDate.format(d1);
        
        // Trả về một mảng chứa các giá trị đã định dạng
        return acceptValue;
    }
	
	public Date parseStringtoDate(String d1) throws Exception {
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date accepttimeValue = formatDate.parse(d1);
			
			return accepttimeValue;
		} catch (Exception e) {
			return null;
		}
	}
	
}
