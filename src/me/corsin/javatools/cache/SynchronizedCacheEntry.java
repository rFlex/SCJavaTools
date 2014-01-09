/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// SynchronizedCacheEntry.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:28:20 PM
////////

package me.corsin.javatools.cache;

public class SynchronizedCacheEntry<T> extends CacheEntry<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Object lock;

	////////////////////////
	// CONSTRUCTORS
	////////////////

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
	
	public T getUpToDateObject() throws Exception {
		synchronized (this.lock) {
			return super.getUpToDateObject();
		}
	}

}
