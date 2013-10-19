/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// SimpleTask.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 19, 2013 at 2:29:09 PM
////////

package me.corsin.javatools.task;

public abstract class SimpleTask extends AbstractTask<SimpleTask, SimpleTask.TaskListener> {

	public static interface TaskListener extends ITaskListener<SimpleTask> { }

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////
	
	protected abstract void perform() throws Throwable;
	
	@Override
	protected void handle() throws Throwable {
		this.perform();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
