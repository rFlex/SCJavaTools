/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// If.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 1:13:56 PM
////////

package me.corsin.javatools.dynamictext;

public class If {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public If() {
		
	}

	////////////////////////
	// METHODS
	////////////////
	
	public boolean isNull(Object object) {
		return object == null;
	}
	
	public boolean notNull(Object object) {
		return object != null;
	}
	
	public boolean greaterThanZero(Number number) {
		return number.doubleValue() > 0.0;
	}
	
	public boolean equalsZero(Number number) {
		return number.doubleValue() == 0;
	}
	
	public boolean lessThanZero(Number number) {
		return number.doubleValue() < 0.0;
	}
	
	public boolean greaterThan(Number number, Number oth) {
		return number.doubleValue() > oth.doubleValue();
	}
	
	public boolean lessThan(Number number, Number oth) {
		return number.doubleValue() < oth.doubleValue();
	}
	
	public boolean equals(Number number, Number oth) {
		return number.doubleValue() == oth.doubleValue();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
