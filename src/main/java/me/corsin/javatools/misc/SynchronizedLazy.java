/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// SynchronizedLazy.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jul 15, 2014 at 5:01:16 PM
////////

package me.corsin.javatools.misc;

public abstract class SynchronizedLazy<T> extends Lazy<T> {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public SynchronizedLazy() {

	}

	////////////////////////
	// METHODS
	////////////////

	@Override
	protected void handleCreation() {
		synchronized (this) {
			if (!this.hasValue()) {
				super.handleCreation();
			}
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
