/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// CacheInfo.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:11:14 PM
////////

package me.corsin.javatools.cache;

import me.corsin.javatools.timer.TimeSpan;

import org.joda.time.DateTime;

public class CacheEntry<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private T object;
	private DateTime lastRefreshedDate;
	private DateTime nextRefreshDate;
	private int refreshIntervalSeconds;
	private CacheEntryRefresher<T> refresher;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public CacheEntry(TimeSpan timeSpan, CacheEntryRefresher<T> refresher) {
		this((int)timeSpan.getTotalSeconds(), refresher);
	}
	
	public CacheEntry(int refreshIntervalSeconds, CacheEntryRefresher<T> refresher) {
		this.refreshIntervalSeconds = refreshIntervalSeconds;
		this.refresher = refresher;
	}
	
	public CacheEntry() {
		
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public T getUpToDateObject(CacheEntryRefresher<T> refresher) throws Exception {
		boolean shouldRefresh = false;
		
		DateTime currentTime = DateTime.now();
		if (lastRefreshedDate == null || this.nextRefreshDate.isBefore(currentTime)) {
			shouldRefresh = true;
		}
		
		if (shouldRefresh) {
			if (refresher != null) {
				this.object = refresher.refreshData();
				this.lastRefreshedDate = currentTime;
				this.nextRefreshDate = this.lastRefreshedDate.plusSeconds(this.refreshIntervalSeconds);
			}
		}
		
		return this.object;
	}
	
	public T getUpToDateObject() throws Exception {
		return this.getUpToDateObject(this.refresher);
	}
	
	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public DateTime getLastRefreshedDate() {
		return lastRefreshedDate;
	}

	public CacheEntryRefresher<T> getRefresher() {
		return refresher;
	}

	public void setRefresher(CacheEntryRefresher<T> refresher) {
		this.refresher = refresher;
	}

	public int getRefreshIntervalSeconds() {
		return refreshIntervalSeconds;
	}

	public void setRefreshIntervalSeconds(int refreshIntervalSeconds) {
		this.refreshIntervalSeconds = refreshIntervalSeconds;
	}

	public DateTime getNextRefreshDate() {
		return nextRefreshDate;
	}

}
