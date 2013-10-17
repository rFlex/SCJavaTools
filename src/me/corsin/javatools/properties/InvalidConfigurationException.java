/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.properties
// InvalidConfigurationException.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 1:04:33 PM
////////

package me.corsin.javatools.properties;

public class InvalidConfigurationException extends RuntimeException {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	private static final long serialVersionUID = -7987324650849654812L;

	public InvalidConfigurationException(String message) {
		super("Invalid configuration: " + message);
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
