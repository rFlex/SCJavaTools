/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// ThreadedPeriodicTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 15, 2014 at 2:54:04 PM
////////

package me.corsin.javatools.task;

import me.corsin.javatools.timer.TimeSpan;

/**
 * TaskQueue that flushes automatically every given time.
 * Tasks are processed sequentially in the same thread.
 * Order in which tasks are processed are guaranted to be the same that the order in which
 * they were added, one task will start to be processed after the previous one has completed
 * @author simoncorsin
 *
 */
public class ThreadedPeriodicTaskQueue extends TaskQueue implements Runnable {

	////////////////////////
	// VARIABLES
	////////////////

	private TimeSpan periodicTimeSpan;
	private TaskQueueThread taskQueueThread;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ThreadedPeriodicTaskQueue() {
		this.taskQueueThread = new TaskQueueThread(this,  this, "PeriodicTaskQueueThread");
	}

	////////////////////////
	// METHODS
	////////////////

	@Override
	public void run() {
		while (!this.isClosed()) {
			this.flushTasks();

			TimeSpan timeSpan = this.periodicTimeSpan;
			long timeToWait = timeSpan != null ? timeSpan.getTotalMs() : 0;

			try {
				Thread.sleep(timeToWait);
			} catch (InterruptedException e) { }
		}
	}

	@Override
	public void close() {
		super.close();

		if (this.taskQueueThread != null) {
			try {
				this.taskQueueThread.join();
			} catch (InterruptedException e) { }
			this.taskQueueThread = null;
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public TimeSpan getPeriodicTimeSpan() {
		return this.periodicTimeSpan;
	}

	public void setPeriodicTimeSpan(TimeSpan periodicTimeSpan) {
		this.periodicTimeSpan = periodicTimeSpan;
	}

}
