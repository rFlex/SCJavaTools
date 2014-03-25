package me.corsin.javatools.timer;
/////////////////////////////////////////////////
// Project : killing-sight
// Package : com.kerious.killingsight.utils
// TimeDiff.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 7, 2012 at 5:25:46 PM
////////

public class TimeSpan {

	////////////////////////
	// VARIABLES
	////////////////
	
	public final static float DAY = TimeSpan.HOUR * 24;
	public final static float HOUR = TimeSpan.MINUTE * 60;
	public final static float MINUTE = TimeSpan.SECOND * 60;
	public final static float SECOND = 1;
	public final static float MILLISECOND = 0.001f;
	private float totalSeconds;
	private int days;
	private int hours;
	private int minutes;
	private int seconds;
	private int milliseconds;
	private boolean totalSecondsDirty;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public TimeSpan() {
		
	}
	
	public TimeSpan(int days, int hours, int minutes, int seconds, int milliseconds) {
		this.setDays(days);
		this.setHours(hours);
		this.setMinutes(minutes);
		this.setSeconds(seconds);
		this.setMilliseconds(milliseconds);
	}
	
	public TimeSpan(float totalSeconds) {
		this.setTotalSeconds(totalSeconds);
	}
	
	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public String toString() {
		StringBuilder date = new StringBuilder();

		if (this.days > 0) {
			date.append(this.days);
			date.append(" days ");
		}
		if (this.hours > 0) {
			date.append(this.hours);
			date.append(" hours ");
		}
		if (this.minutes > 0) {
			date.append(this.minutes);
			date.append(" minutes ");
		}
		if (this.seconds > 0) {
			date.append(this.seconds);
			date.append(" seconds ");
		}
		date.append(this.milliseconds);
		date.append(" milliseconds");
		
		return date.toString();
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public float getTotalSeconds() {
		if (this.totalSecondsDirty) {
			this.totalSeconds = this.days * DAY + this.hours * HOUR + this.minutes * MINUTE + this.seconds * SECOND + this.milliseconds * MILLISECOND;
			this.totalSecondsDirty = false;
		}
		
		return totalSeconds;
	}
	
	public long getTotalMs() {
		return (long)(this.getTotalSeconds() * 1000f);
	}

	public TimeSpan setTotalSeconds(float totalSeconds) {
		this.totalSeconds = totalSeconds;
		
		this.days = (int)(totalSeconds / TimeSpan.DAY);
		totalSeconds -= this.days * TimeSpan.DAY;
		
		this.hours = (int)(totalSeconds / TimeSpan.HOUR);
		totalSeconds -= this.hours * TimeSpan.HOUR;
		
		this.minutes = (int)(totalSeconds / TimeSpan.MINUTE);
		totalSeconds -= this.minutes * TimeSpan.MINUTE;
		
		this.seconds = (int)totalSeconds;
		this.milliseconds = (int)(totalSeconds * 1000f) - (this.seconds * 1000);
		
		return this;
	}

	public int getDays() {
		return days;
	}

	public TimeSpan setDays(int days) {
		this.days = days;
		this.totalSecondsDirty = true;
		
		return this;
	}

	public int getHours() {
		return hours;
	}

	public TimeSpan setHours(int hours) {
		this.hours = hours;
		this.totalSecondsDirty = true;
		
		return this;
	}

	public int getMinutes() {
		return minutes;
	}

	public TimeSpan setMinutes(int minutes) {
		this.minutes = minutes;
		this.totalSecondsDirty = true;
		
		return this;
	}

	public int getSeconds() {
		return seconds;
	}

	public TimeSpan setSeconds(int seconds) {
		this.seconds = seconds;
		this.totalSecondsDirty = true;
		
		return this;
	}

	public int getMilliseconds() {
		return milliseconds;
	}

	public TimeSpan setMilliseconds(int milliseconds) {
		this.milliseconds = milliseconds;
		this.totalSecondsDirty = true;
		
		return this;
	}
}
