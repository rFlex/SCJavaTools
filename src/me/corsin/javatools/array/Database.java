/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.array
// Database.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 8:15:29 PM
////////

package me.corsin.javatools.array;

import java.util.List;

public interface Database<KeyType, ValueType> {

	void put(KeyType key, ValueType value);
	ValueType get(KeyType key);
	KeyType insert(ValueType value);
	void remove(KeyType key);
	List<ValueType> getAll();
	
}
