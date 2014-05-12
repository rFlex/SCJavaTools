/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.wsframework.utils
// Pointer.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jul 1, 2013 at 5:37:17 PM
////////

package me.corsin.javatools.misc;

public class ValueHolder<T> {
	
	public static class BooleanHolder extends ValueHolder<Boolean> {}
	public static class IntegerHolder extends ValueHolder<Integer> {}
	public static class FloatHolder extends ValueHolder<Float> {}
	public static class DoubleHolder extends ValueHolder<Double> {}
	public static class StringHolder extends ValueHolder<String> {}
	public static class ObjectHolder extends ValueHolder<Object> {}

	////////////////////////
	// VARIABLES
	////////////////

	private T value;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public ValueHolder() {
		
	}
	
	public ValueHolder(T value) {
		this.value = value;
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final T value() {
		return value;
	}

	public final void setValue(T value) {
		this.value = value;
	}
	
	public final boolean hasValue() {
		return this.value != null;
	}
	
}
