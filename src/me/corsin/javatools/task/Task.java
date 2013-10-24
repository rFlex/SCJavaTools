/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// Task.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 19, 2013 at 2:26:50 PM
////////

package me.corsin.javatools.task;

public abstract class Task<T> extends AbstractTask<Task<T>, Task.TaskListener<T>> {

	public static interface TaskListener<T> extends ITaskListener<Task<T>> { }

	////////////////////////
	// VARIABLES
	////////////////
	
	private T returnedValue;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public Task() {
		
	}
	
	public Task(Object creator) {
		super(creator);
	}
	
	////////////////////////
	// METHODS
	////////////////

	protected abstract T perform() throws Throwable;
	
	@Override
	protected void handle() throws Throwable {
		this.returnedValue = this.perform();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public T getReturnedValue() {
		this.waitCompletion();
		return returnedValue;
	}

}
