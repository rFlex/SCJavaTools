/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// SynchronizedCacheEntry.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:28:20 PM
////////

package me.corsin.javatools.cache;

import me.corsin.javatools.timer.TimeSpan;

public class SynchronizedCacheEntry<T> extends CacheEntry<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Object lock;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public SynchronizedCacheEntry(TimeSpan timeSpan, CacheEntryRefresher<T> refresher) {
		this((int)timeSpan.getTotalSeconds(), refresher);
	}
	
	public SynchronizedCacheEntry(int refreshIntervalSeconds, CacheEntryRefresher<T> refresher) {
		super(refreshIntervalSeconds, refresher);

		this.lock = new Object();
	}
	
	public SynchronizedCacheEntry() {
		this.lock = new Object();
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public T getUpToDateObject(CacheEntryRefresher<T> refresher) throws Exception {
		synchronized (this.lock) {
			return super.getUpToDateObject(refresher);
		}
	}

}
