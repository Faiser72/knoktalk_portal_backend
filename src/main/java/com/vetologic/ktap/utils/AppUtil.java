package com.vetologic.ktap.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppUtil {
	public static String currentDateWithTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		String time = timeFormat.format(calendarDate.getTime());
		return date + " " + time;
	}

	public static String currentDateWithoutTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		return date;
	}

}
