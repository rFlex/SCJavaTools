/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// DataCache.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:06:58 PM
////////

package me.corsin.javatools.cache;

import java.util.HashMap;
import java.util.Map;

import me.corsin.javatools.misc.NullArgumentException;

public class TemporalDataCache<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Map<String, CacheEntry<T>> objects;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public TemporalDataCache() {
		this.objects = new HashMap<String, CacheEntry<T>>();
	}

	////////////////////////
	// METHODS
	////////////////
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public T get(String key) throws Exception {
		CacheEntry<T> entry = this.objects.get(key);
		
		return entry != null ? entry.getUpToDateObject() : null;
	}
	
	public void set(String key, CacheEntry<T> cacheEntry) {
		if (key == null) {
			throw new NullArgumentException("key");
		}
		if (cacheEntry == null) {
			throw new NullArgumentException("cacheEntry");
		}
		
		this.objects.put(key, cacheEntry);
	}
}
