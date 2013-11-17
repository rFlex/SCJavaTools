package me.corsin.javatools.misc;
/////////////////////////////////////////////////
// Project : killing-sight
// Package : com.kerious.killingsight.world.entities
// Pool.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 20, 2012 at 10:00:37 PM
////////

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class Pool<T> {

	////////////////////////
	// VARIABLES
	////////////////

	private Queue<T> objects;
	private int maxObjects;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public Pool() {
		this.objects = new ArrayDeque<T>();
		this.maxObjects = Integer.MAX_VALUE;
	}
	
	////////////////////////
	// METHODS
	////////////////

	abstract protected T instantiate();
	
	public T obtain() {
		T obj = null;
		
		if (!this.objects.isEmpty()) {
			obj = this.objects.poll();
		} else {
			obj = this.instantiate();
		}
		
		if (obj instanceof Poolable) {
			Poolable poolable = (Poolable)obj;
			poolable.setPool(this);
		}
		
		return obj;
	}
	
	public void release(T obj) {
		if (this.getRetainedObjects() < this.maxObjects) {
			if (obj instanceof Poolable) {
				Poolable poolable = (Poolable)obj;
				poolable.reset();
			}
			
			this.objects.add(obj);
		}
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final int getRetainedObjects() {
		return this.objects.size();
	}

	public final int getMaxObjects() {
		return maxObjects;
	}

	public final void setMaxObjects(int maxObjects) {
		this.maxObjects = maxObjects;
	}
}
