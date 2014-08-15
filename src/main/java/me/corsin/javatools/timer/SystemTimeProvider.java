/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.timer
// SystemTimeProvider.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 14, 2014 at 4:55:24 PM
////////

package me.corsin.javatools.timer;

public class SystemTimeProvider implements TimeProvider {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public SystemTimeProvider() {

	}


	////////////////////////
	// METHODS
	////////////////

	@Override
	public long getCurrentTimeMs() {
		return System.currentTimeMillis();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
