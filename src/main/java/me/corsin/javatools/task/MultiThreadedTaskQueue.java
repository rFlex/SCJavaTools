/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// MultiThreadedTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 19, 2013 at 2:31:50 PM
////////

package me.corsin.javatools.task;

/**
 * TaskQueue that flushes automatically when it has any task pending.
 * Tasks can be processed in any thread hold by this TaskQueue.
 * Order in which task are processed are guaranted to be the same that the order in which
 * they were added, however as this TaskQueue allows concurrent Tasks, one task may start
 * after the previous one didn't finish yet.
 * @author simoncorsin
 *
 */
@Deprecated
public class MultiThreadedTaskQueue extends ThreadedConcurrentTaskQueue {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public MultiThreadedTaskQueue() {
		super();
	}

	public MultiThreadedTaskQueue(int threadCount) {
		super(threadCount);
	}

	////////////////////////
	// METHODS
	////////////////


	////////////////////////
	// GETTERS/SETTERS
	////////////////

}
