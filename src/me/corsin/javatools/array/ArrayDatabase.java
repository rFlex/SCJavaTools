/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.array
// ArrayDatabase.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 8:15:20 PM
////////

package me.corsin.javatools.array;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ArrayDatabase<T> implements Database<Integer, T> {

	////////////////////////
	// VARIABLES
	////////////////
	
	private List<T> data;
	private Queue<Integer> freePositions;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public ArrayDatabase() {
		this.data = new ArrayList<T>();
		this.freePositions = new ArrayDeque<Integer>();
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public synchronized void put(Integer key, T value) {
		int intKey = key;
		
		while (this.data.size() <= intKey) {
			this.data.add(null);
		}
		if (this.data.get(intKey) != null) {
			throw new DatabaseException("Database already has a value for key [" + key + "]");
		}
		this.data.set(intKey, value);
	}

	@Override
	public synchronized T get(Integer key) {
		int intKey = key;
		T ret = null;
		
		if (intKey >= 0 || intKey < this.data.size()) {
			ret = this.data.get(intKey);
		}
		
		return ret;
	}

	@Override
	public synchronized Integer insert(T value) {
		Integer position = null;
		
		if (!this.freePositions.isEmpty()) {
			position = this.freePositions.poll();
		} else {
			position = this.data.size();
		}
		
		this.put(position, value);
		
		return position;
	}

	@Override
	public synchronized void remove(Integer key) {
		if (this.get(key) != null) {
			this.data.set(key, null);
			this.freePositions.add(key);
		}
	}

	@Override
	public List<T> getAll() {
		List<T> elements = new ArrayList<T>();
		
		for (T element : this.data) {
			if (element != null) {
				elements.add(element);
			}
		}
		
		return elements;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
