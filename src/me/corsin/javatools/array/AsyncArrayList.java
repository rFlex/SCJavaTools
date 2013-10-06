package me.corsin.javatools.array;
/////////////////////////////////////////////////
// Project : kerious-framework
// Package : com.kerious.framework.utils
// AsyncArrayList.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Dec 9, 2012 at 1:02:55 AM
////////



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsyncArrayList<T> implements Iterable<T> {

	////////////////////////
	// VARIABLES
	////////////////

	private List<T> list;
	private List<T> toAdd;
	private List<T> toRemove;
	private boolean locked;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public AsyncArrayList() {
		this.list = new ArrayList<T>(); 
		this.toAdd = new ArrayList<T>();
		this.toRemove = new ArrayList<T>();
	}

	////////////////////////
	// METHODS
	////////////////

	public void add(T element) {
		if (this.locked) {
			this.toAdd.add(element);
		} else {
			this.list.add(element);
		}
	}

	public boolean remove(T element) {
		if (this.locked) {
			this.toRemove.add(element);
			
			return this.list.contains(element);
		} else {
			return this.list.remove(element);
		}
	}

	public void lock() {
		this.locked = true;
	}

	public void unlock() {
		for (int i = 0; i < this.toAdd.size(); i++) {
			this.list.add(this.toAdd.get(i));
		}
		this.toAdd.clear();

		for (int i = 0; i < this.toRemove.size(); i++) {
			this.list.remove(this.toRemove.get(i));
		}
		this.toRemove.clear();

		this.locked = false;
	}

	@Override
	public Iterator<T> iterator() {
		return this.list.iterator();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public int size() {
		return this.list.size();
	}
	
	public T get(int index) {
		return this.list.get(index);
	}
}
