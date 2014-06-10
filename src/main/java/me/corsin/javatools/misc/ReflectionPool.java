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
	
	public ReflectionPool() {
		
	}
	
	public ReflectionPool(Class<T> objectClass) {
		this.setObjectClass(objectClass);
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	protected T instantiate() {
		if (this.objectClass == null) {
			throw new NullArgumentException("No objectClass set inside the reflection pool");
		}
		
		try {
			return this.objectClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Unable to instantiate class " + objectClass.getSimpleName(), e);
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public Class<T> getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(Class<T> objectClass) {
		this.objectClass = objectClass;
	}	
}
