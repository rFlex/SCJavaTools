/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Lazy.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jul 15, 2014 at 4:57:14 PM
////////

package me.corsin.javatools.misc;

public abstract class Lazy<T> {

	////////////////////////
	// VARIABLES
	////////////////

	protected T value;
	private boolean hasValue;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public Lazy() {

	}

	////////////////////////
	// METHODS
	////////////////

	abstract protected T createValue();

	protected void handleCreation() {
		this.value = this.createValue();
		this.hasValue = true;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public T getValue() {
		if (!this.hasValue) {
			this.handleCreation();
		}

		return this.value;
	}

	public boolean hasValue() {
		return this.hasValue;
	}
}
