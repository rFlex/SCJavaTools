/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// SingleThreadedTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 8:01:17 PM
////////

package me.corsin.javatools.task;

public class SingleThreadedTaskQueue extends MultiThreadedTaskQueue {

	////////////////////////
	// VARIABLES
	////////////////
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SingleThreadedTaskQueue() {
		super(1);
	}

	////////////////////////
	// METHODS
	////////////////
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
