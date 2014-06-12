/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// MultiThreadedTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 19, 2013 at 2:31:50 PM
////////

package me.corsin.javatools.task;

public class MultiThreadedTaskQueue extends TaskQueue implements Runnable {

	////////////////////////
	// VARIABLES
	////////////////
	
	private TaskQueueThread[] threads;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public MultiThreadedTaskQueue() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	public MultiThreadedTaskQueue(int threadCount) {
		this.threads = new TaskQueueThread[threadCount];
		
		for (int i = 0, length = threads.length; i < length; i++) {
			this.threads[i] = new TaskQueueThread(this, this, "TaskQueueThread #" + i);
			this.threads[i].start();
		}
	}

	////////////////////////
	// METHODS
	////////////////

	@Override
	public void run() {
		while (!this.isClosed()) {
			this.waitForTasks();
			this.flushTasks();
		}
	}
	
	@Override
	public void close() {
		super.close();
		this.threads = null;
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public boolean useDaemonThreads() {
		if (this.threads.length > 0) {
			return this.threads[0].isDaemon();
		}
		
		return false;
	}
	
	public void setUseDaemonThreads(boolean daemon) {
		for (TaskQueueThread thread : this.threads) {
			thread.setDaemon(daemon);
		}
	}
	
	public int getThreadCount() {
		return this.threads.length;
	}
}
