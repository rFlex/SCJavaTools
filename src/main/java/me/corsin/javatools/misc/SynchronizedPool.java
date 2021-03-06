/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// SynchronizedPool.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 17, 2013 at 7:15:59 PM
////////

package me.corsin.javatools.misc;

import me.corsin.javatools.misc.ValueHolder.BooleanHolder;

public abstract class SynchronizedPool<T> extends Pool<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Object lock;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SynchronizedPool() {
		super();
		
		this.lock = new Object();
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public T obtain(BooleanHolder wasInstanciatedHolder) {
		synchronized (this.lock) {
			return super.obtain(wasInstanciatedHolder);
		}
	}
	
	
	public void release(T obj) {
		synchronized (this.lock) {
			super.release(obj);
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
