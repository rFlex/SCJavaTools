/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// PoolableImpl.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Nov 12, 2013 at 5:40:33 PM
////////

package me.corsin.javatools.misc;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PoolableImpl implements Poolable {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Pool pool;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public void reset() {
	}

	@Override
	public void release() {
		if (this.pool != null) {
			this.pool.release(this);
			this.pool = null;
		} else {
			throw new RuntimeException("Too many releases on " + this.getClass().getSimpleName());
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	@Override
	public void setPool(Pool pool) {
		this.pool = pool;
	}

	@Override
	public Pool getPool() {
		return this.pool;
	}
}
