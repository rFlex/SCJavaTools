/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// SynchronizedReflectionPool.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 9, 2014 at 10:00:04 PM
////////

package me.corsin.javatools.misc;

import me.corsin.javatools.misc.ValueHolder.BooleanHolder;

public class SynchronizedReflectionPool<T> extends ReflectionPool<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Object lock;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SynchronizedReflectionPool() {
		this(null);
	}
	
	public SynchronizedReflectionPool(Class<T> cls) {
		super(cls);
		
		this.lock = new Object();
	}

	////////////////////////
	// METHODS
	////////////////
	
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
