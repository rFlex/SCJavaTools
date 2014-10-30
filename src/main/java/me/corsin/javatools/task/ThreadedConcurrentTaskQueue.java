/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// ThreadedConcurrentTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 15, 2014 at 2:42:58 PM
////////

package me.corsin.javatools.task;

/**
 * TaskQueue that flushes automatically when it has any task pending.
 * Tasks can be processed in any thread hold by this TaskQueue.
 * Order in which tasks are processed are guaranted to be the same that the order in which
 * they were added, however as this TaskQueue allows concurrent Tasks, one task may start
 * after the previous one didn't finish yet.
 * @author simoncorsin
 *
 */
public class ThreadedConcurrentTaskQueue extends TaskQueue implements Runnable {

	////////////////////////
	// VARIABLES
	////////////////

	private TaskQueueThread[] threads;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ThreadedConcurrentTaskQueue() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public ThreadedConcurrentTaskQueue(int threadCount) {
		this.threads = new TaskQueueThread[threadCount];

		for (int i = 0, length = this.threads.length; i < length; i++) {
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
		if (this.threads != null) {
			for (TaskQueueThread taskQueueThread : this.threads) {
				try {
					taskQueueThread.join();
				} catch (InterruptedException e) { }
			}
			this.threads = null;
		}
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
