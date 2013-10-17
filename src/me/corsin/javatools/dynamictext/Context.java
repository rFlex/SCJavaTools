/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// DynamicContext.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:42:36 PM
////////

package me.corsin.javatools.dynamictext;

import java.util.HashMap;
import java.util.Map;

public class Context {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Map<String, Object> context;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public Context() {
		this.context = new HashMap<String, Object>();
	}

	////////////////////////
	// METHODS
	////////////////
	
	public void put(String key, Object value) {
		this.context.put(key, value);
	}
	
	public void remove(String key) {
		this.context.remove(key);
	}
	
	public Object get(String key) {
//		if (!this.context.containsKey(key)) {
//			throw new InvalidTextException("No such identifier " + key);
//		}
		
		return this.context.get(key);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public boolean contains(String key) {
		return this.context.containsKey(key);
	}
}
