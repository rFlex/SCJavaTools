/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.webservice.utils
// DateUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 13, 2013 at 7:34:55 PM
////////

package me.corsin.javatools.date;

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
	
	public static Date getDateOffsetedFromNow(int calendarField, int amount) {
		return getDateOffseted(new Date(), calendarField, amount);
	}
	
	public static Date getDateOffseted(Date date, int calendarField, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarField, amount);

		return calendar.getTime();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
