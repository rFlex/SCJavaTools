/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.properties
// SharedProperties.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 12:49:46 PM
////////

package me.corsin.javatools.properties;

import java.util.HashMap;
import java.util.Map;

public class SharedProperties {

	////////////////////////
	// VARIABLES
	////////////////
	
	private static SharedProperties sharedInstance = null;
	
	private Map<String, Object> properties;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SharedProperties() {
		this.properties = new HashMap<String, Object>();
	}

	////////////////////////
	// METHODS
	////////////////
	
	public void putSynchronized(String propertyName, Object propertyValue) {
		synchronized (this) {
			this.put(propertyName, propertyValue);
		}
	}
	
	public void put(String propertyName, Object propertyValue) {
		this.properties.put(propertyName, propertyValue);
	}
	
	public Object get(String propertyName) {
		return this.properties.get(propertyName);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public static SharedProperties getSharedInstance() {
		if (sharedInstance == null) {
			synchronized (SharedProperties.class) {
				if (sharedInstance == null) {
					sharedInstance = new SharedProperties();
				}
			}
		}
		return sharedInstance;
	}
	
	public static boolean hasSharedInstance() {
		return sharedInstance != null;
	}
}
