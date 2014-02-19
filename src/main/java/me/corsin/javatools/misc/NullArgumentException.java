/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// NullArgumentException.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 23, 2013 at 4:18:26 PM
////////

package me.corsin.javatools.misc;

public class NullArgumentException extends IllegalArgumentException {

	////////////////////////
	// VARIABLES
	////////////////
	
	private static final long serialVersionUID = -4199477229066551880L;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public NullArgumentException(String argumentName) {
		super(argumentName + " may not be null");
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
