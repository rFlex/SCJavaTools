/////////////////////////////////////////////////
// Project : killing-sight
// Package : com.kerious.killingsight.utils
// StopWatch.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 7, 2012 at 3:35:24 PM
////////

package me.corsin.javatools.timer;

public class StopWatch {

	////////////////////////
	// VARIABLES
	////////////////

	private TimeProvider timeProvider;
	public long timeAtStart;

	////////////////////////
	// NESTED CLASSES
	////////////////
	
	public static interface TimeProvider {
		long getCurrentTimeMs();
	}
	
	////////////////////////
	// CONSTRUCTORS
	////////////////

	public StopWatch(TimeProvider timeProvider) {
		this.setTimeProvider(timeProvider);
		this.start();
	}
	
	public StopWatch() {
		this.setTimeProvider(null);
		this.start();
	}
	
	////////////////////////
	// METHODS
	////////////////

	public void start() {
		this.timeAtStart = this.timeProvider.getCurrentTimeMs();
	}
	
	public float secondCurrent() {
		final long timeDecal = this.timeProvider.getCurrentTimeMs() - this.timeAtStart;
		
		return ((float)timeDecal) / 1000f;
	}
	
	public String stringCurrent() {
		TimeDiff timeDiff = this.timeDiffCurrent();
		String date = "";

		if (timeDiff.days > 0) {
			date += timeDiff.days + " days ";
		}
		if (timeDiff.hours > 0) {
			date += timeDiff.hours + " hours ";
		}
		if (timeDiff.minutes > 0) {
			date += timeDiff.minutes + " minutes ";
		}
		if (timeDiff.seconds > 0) {
			date += timeDiff.seconds + " seconds ";
		}
		date += timeDiff.milliseconds + " milliseconds";
		
		return date;
	}
	
	public TimeDiff timeDiffCurrent() {
		return new TimeDiff(this.secondCurrent());
	}
	
	public TimeDiff timeDiffCurrent(TimeDiff diff) {
		diff.create(this.secondCurrent());
		return diff;
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final void setTimeProvider(TimeProvider timeProvider) {
		if (timeProvider == null) {
			timeProvider = new TimeProvider() {

				@Override
				public long getCurrentTimeMs() {
					return System.currentTimeMillis();
				}
				
			};
		}
		
		this.timeProvider = timeProvider;
	}
}
