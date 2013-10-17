/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Pair.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 8:31:54 PM
////////

package me.corsin.javatools.misc;

public class Pair<T, T2> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private T first;
	private T2 second;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public Pair(T first, T2 second) {
		this.first = first;
		this.second = second;
	}
	
	public Pair() {
		
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public T getFirst() {
		return first;
	}

	public void setFirst(T first) {
		this.first = first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}
}
