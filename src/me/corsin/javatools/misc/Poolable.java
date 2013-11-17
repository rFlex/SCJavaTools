/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Poolable.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 12, 2013 at 5:37:16 PM
////////

package me.corsin.javatools.misc;

@SuppressWarnings("rawtypes")
public interface Poolable {
	
	void reset();
	void release();

	void setPool(Pool pool);
	Pool getPool();
	
}
