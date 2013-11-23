/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// ReflectionPool.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 23, 2013 at 7:38:22 PM
////////

package me.corsin.javatools.misc;

public class ReflectionPool<T> extends Pool<T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Class<T> objectClass;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public ReflectionPool(Class<T> objectClass) {
		if (objectClass == null) {
			throw new NullArgumentException("objectClass");
		}
		this.objectClass = objectClass;
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	protected T instantiate() {
		try {
			return this.objectClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
