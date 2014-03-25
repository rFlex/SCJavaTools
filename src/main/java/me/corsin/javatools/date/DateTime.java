/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.date
// DateTime.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Mar 1, 2014 at 2:58:29 PM
////////

package me.corsin.javatools.date;

import java.util.Date;

public class DateTime extends Date {

	////////////////////////
	// VARIABLES
	////////////////

	private static final long serialVersionUID = -1679159852172983463L;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////

	public DateTime(int year, int month, int day) {
		super(DateUtils.getTimeMillis(year, month, day));
	}

	////////////////////////
	// METHODS
	////////////////
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
