/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// ThreadedSequentialTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 15, 2014 at 2:45:22 PM
////////

package me.corsin.javatools.task;

/**
 * TaskQueue that flushes automatically when it has any task pending.
 * Tasks are processed sequentially in the same thread.
 * Order in which tasks are processed are guaranted to be the same that the order in which
 * they were added, one task will start to be processed after the previous one has completed
 * @author simoncorsin
 *
 */
public class ThreadedSequentialTaskQueue extends ThreadedConcurrentTaskQueue {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ThreadedSequentialTaskQueue() {
		super(1);
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
