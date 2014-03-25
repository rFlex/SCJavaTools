/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.webservice.utils
// DateUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 13, 2013 at 7:34:55 PM
////////

package me.corsin.javatools.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////
	
	public static Date currentTimeGMT() {
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone   
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

		//Time in GMT
		try {
			return dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String toISO8601String(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'Z'");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String value = df.format(date);
		
		return value;
	}
	
	public static Date getDateOffsetedFromNow(int calendarField, int amount) {
		return getDateOffseted(new Date(), calendarField, amount);
	}
	
	public static Date getDateOffseted(Date date, int calendarField, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarField, amount);

		return calendar.getTime();
	}
	
	public static long getTimeMillis(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
//		System.out.println();
		return calendar.getTimeInMillis();
	}
	
	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		return calendar.getTime();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
