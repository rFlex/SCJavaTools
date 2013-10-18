/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// SingleThreadedTaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 8:01:17 PM
////////

package me.corsin.javatools.task;

import me.corsin.javatools.misc.Disposable;

public class SingleThreadedTaskQueue extends TaskQueue implements Runnable, Disposable {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Thread thread;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public SingleThreadedTaskQueue() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public void run() {
		while (!this.isDisposed()) {
			synchronized (this.pendingTasks) {
				if (this.pendingTasks.isEmpty()) {
					try {
						this.pendingTasks.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			this.flushTasks();
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.thread = null;
		
		synchronized (this.pendingTasks) {
			this.pendingTasks.notifyAll();
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
