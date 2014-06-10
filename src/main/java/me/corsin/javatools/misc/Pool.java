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
import java.util.Deque;

import me.corsin.javatools.misc.ValueHolder.BooleanHolder;

public abstract class Pool<T> {

	////////////////////////
	// VARIABLES
	////////////////

	private Deque<T> objects;
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
		return this.obtain(null);
	}
	
	public T obtain(BooleanHolder wasInstanciatedHolder) {
		T obj = null;

		boolean wasInstanciated = false;
		if (!this.objects.isEmpty()) {
			obj = this.objects.poll();
		} else {
			obj = this.instantiate();
			wasInstanciated = true;
		}
		
		if (obj instanceof Poolable) {
			Poolable poolable = (Poolable)obj;
			poolable.setPool(this);
			
			if (wasInstanciated) {
				poolable.reset();
			}
		}
		
		if (wasInstanciatedHolder != null) {
			wasInstanciatedHolder.setValue(wasInstanciated);
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
	
	/**
	 * Instantiate quantity numbers of elements from the Pool
	 * and set them in the pool right away
	 * @param quantity
	 */
	@SuppressWarnings("unchecked")
	public void preload(int quantity) {
		Object[] objects = new Object[quantity];
		
		for (int i = 0; i < quantity; i++) {
			objects[i] = this.instantiate();
		}
		
		for (int i = 0; i < quantity; i++) {
			this.release((T)objects[i]);
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

		while (maxObjects > this.objects.size()) {
			this.objects.remove();
		}
	}
}
