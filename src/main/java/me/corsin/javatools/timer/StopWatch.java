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
		return this.timeSpanCurrent().toString();
	}
	
	public TimeSpan timeSpanCurrent() {
		return new TimeSpan(this.secondCurrent());
	}
	
	public TimeSpan timeSpanCurrent(TimeSpan diff) {
		diff.setTotalSeconds(this.secondCurrent());
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
