/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.string
// InvalidTextException.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 3:11:10 PM
////////

package me.corsin.javatools.dynamictext;

public class InvalidTextException extends RuntimeException {

	////////////////////////
	// VARIABLES
	////////////////

	private static final long serialVersionUID = -5668688519371634593L;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////

	public InvalidTextException(String message) {
		this(message, null);
	}
	
	public InvalidTextException(String message, Throwable nestedException) {
		super("Invalid text: " + message, nestedException);
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
