/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// CacheInfo.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:11:14 PM
////////

package me.corsin.javatools.cache;

import java.util.Calendar;
import java.util.Date;

import me.corsin.javatools.date.DateUtils;
import me.corsin.javatools.timer.TimeSpan;

public class CacheEntry<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private T object;
	private Date lastRefreshedDate;
	private Date nextRefreshDate;
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
		
		Date currentTime = DateUtils.currentTimeGMT();
		if (lastRefreshedDate == null || this.nextRefreshDate.before(currentTime)) {
			shouldRefresh = true;
		}
		
		if (shouldRefresh) {
			if (refresher != null) {
				this.object = refresher.refreshData();
				this.lastRefreshedDate = currentTime;
				this.nextRefreshDate = DateUtils.getDateOffseted(this.lastRefreshedDate, Calendar.SECOND, this.refreshIntervalSeconds);
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

	public Date getLastRefreshedDate() {
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

	public Date getNextRefreshDate() {
		return nextRefreshDate;
	}

}
