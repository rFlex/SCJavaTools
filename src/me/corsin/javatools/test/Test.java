/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.test
// Test.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 8:39:49 PM
////////

package me.corsin.javatools.test;

public class Test {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////
	
	protected void ensureEquals(Object obj, Object cmp) {
		if (!obj.equals(cmp)) {
			throw new TestException("Object does not equals. Expected [" + obj.toString() + "], got [" + cmp.toString() + "]");
		}
	}
	
	protected void ensureNotEquals(Object obj, Object cmp) {
		if (obj.equals(cmp)) {
			throw new TestException("Object must not be equals. Got [" + obj.toString() + "]");
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
