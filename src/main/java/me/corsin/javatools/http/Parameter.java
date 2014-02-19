package me.corsin.javatools.http;

import java.io.InputStream;
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
	private Object value;
	private String fileName;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public Parameter(String name, Object value, String fileName) {
		this.name = name;
		this.value = value;
		this.fileName = fileName;
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

	public final Object getValue() {
		return value;
	}

	public final void setValue(Object value) {
		this.value = value;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public final boolean isRawData() {
		return this.value instanceof byte[] || this.value instanceof InputStream;
	}

}
