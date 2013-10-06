/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.webservice.utils
// DateUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 13, 2013 at 7:34:55 PM
////////

package me.corsin.javatools.date;

import java.util.Calendar;
import java.util.Date;

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
