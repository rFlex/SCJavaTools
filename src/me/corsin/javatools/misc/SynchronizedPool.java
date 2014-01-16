/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// SynchronizedPool.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 17, 2013 at 7:15:59 PM
////////

package me.corsin.javatools.misc;

public abstract class SynchronizedPool<T> extends Pool<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Object lockObject;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SynchronizedPool() {
		super();
		
		this.lockObject = new Object();
	}

	////////////////////////
	// METHODS
	////////////////
	
	public T obtain() {
		synchronized (this.lockObject) {
			return super.obtain();
		}
	}
	
	public void release(T obj) {
		synchronized (this.lockObject) {
			super.release(obj);
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
