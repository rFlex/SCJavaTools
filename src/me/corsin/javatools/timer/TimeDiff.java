package me.corsin.javatools.timer;
/////////////////////////////////////////////////
// Project : killing-sight
// Package : com.kerious.killingsight.utils
// TimeDiff.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 7, 2012 at 5:25:46 PM
////////

public class TimeDiff {

	////////////////////////
	// VARIABLES
	////////////////
	
	public final static float DAY = TimeDiff.HOUR * 24;
	public final static float HOUR = TimeDiff.MINUTE * 60;
	public final static float MINUTE = TimeDiff.SECOND * 60;
	public final static float SECOND = 1;
	public float totalSeconds;
	public int days;
	public int hours;
	public int minutes;
	public int seconds;
	public int milliseconds;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public TimeDiff() {
		
	}
	
	public TimeDiff(float timeDiff) {
		this.create(timeDiff);
	}
	
	////////////////////////
	// METHODS
	////////////////

	public void reset() {
		this.totalSeconds = 0;
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.milliseconds = 0;
	}
	
	public void create(float timeDiff) {
		this.reset();
		
		this.totalSeconds = timeDiff;
		
		this.days = (int)(timeDiff / TimeDiff.DAY);
		timeDiff -= this.days * TimeDiff.DAY;
		
		this.hours = (int)(timeDiff / TimeDiff.HOUR);
		timeDiff -= this.hours * TimeDiff.HOUR;
		
		this.minutes = (int)(timeDiff / TimeDiff.MINUTE);
		timeDiff -= this.minutes * TimeDiff.MINUTE;
		
		this.seconds = (int)timeDiff;
		this.milliseconds = (int)(timeDiff * 1000f) - (this.seconds * 1000);
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////

}
