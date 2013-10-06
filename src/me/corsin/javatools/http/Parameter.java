package me.corsin.javatools.http;
/////////////////////////////////////////////////
// Project : exiled-api
// Package : com.kerious.exiled.masterserver.api.communicator
// Parameter.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 24, 2013 at 5:04:27 PM
////////



public class Parameter {

	////////////////////////
	// VARIABLES
	////////////////
	
	private String name;
	private String value;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}
}
