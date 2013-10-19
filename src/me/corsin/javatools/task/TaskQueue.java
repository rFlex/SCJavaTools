/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// TaskQueue.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 7:33:18 PM
////////

package me.corsin.javatools.task;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import me.corsin.javatools.misc.Disposable;

public class TaskQueue implements Disposable {

	////////////////////////
	// VARIABLES
	////////////////
	
	// This variable is notified each time a Runnable is added or when it is empty
	final private Queue<Runnable> tasks;
	// This variable is notified when it reaches zero
	private Object tasksReachesZero;
	private int runningTasks;
	private boolean disposed;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TaskQueue() {
		this.tasks = new ArrayDeque<Runnable>();
		this.tasksReachesZero = new Object();
		this.runningTasks = 0;
	}

	////////////////////////
	// METHODS
	////////////////
	
	private Runnable getNextTask() {
		Runnable task = null;
		synchronized (this.tasks) {
			if (!this.tasks.isEmpty()) {
				task = this.tasks.poll();
				if (this.tasks.isEmpty()) {
					this.tasks.notifyAll();
				}
			}
		}
		return task;
	}
	
	public boolean handleNextTask() {
		Runnable task = this.getNextTask();
		
		if (task != null) {
			synchronized (this.tasksReachesZero) {
				this.runningTasks++;
			}
			try {
				task.run();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			
			synchronized (this.tasksReachesZero) {
				this.runningTasks--;
				if (this.runningTasks == 0) {
					this.tasksReachesZero.notifyAll();
				}
			}
			
			synchronized (task) {
				task.notifyAll();
			}
			return true;
		}
		return false;
	}
	
	public void flushTasks() {
		while (!this.disposed && this.handleNextTask()) {
			
		}
	}
	
	@Override
	public void dispose() {
		this.disposed = true;
		synchronized (this.tasks) {
			this.tasks.clear();
			this.tasks.notifyAll();
		}
	}
	
	public void executeAsync(Runnable runable) {
		synchronized (this.tasks) {
			this.tasks.add(runable);
			// Notify that a task has been added
			this.tasks.notifyAll();
		}
	}
	
	public void executeSync(Runnable runnable) {
		synchronized (runnable) {
			this.executeAsync(runnable);
			try {
				runnable.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executeSyncTimed(Runnable runnable, long inMs) {
		try {
			Thread.sleep(inMs);
			this.executeSync(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void executeAsyncTimed(Runnable runnable, long inMs) {
		final Runnable theRunnable = runnable;
		
		// This implementation is not really suitable for now as the timer uses its own thread
		// The TaskQueue itself should be able in the future to handle this without using a new thread
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				executeAsync(theRunnable);
			}
		}, inMs);
	}
	
	protected void waitForTasks() {
		synchronized (this.tasks) {
			while (!this.disposed && !this.hasTaskPending()) {
				try {
					this.tasks.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void waitAllTasks() {
		synchronized (this.tasks) {
			while (this.hasTaskPending()) {
				try {
					this.tasks.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (this.tasksReachesZero) {
				if (this.runningTasks > 0) {
					try {
						this.tasksReachesZero.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public boolean hasTaskPending() {
		return !this.tasks.isEmpty();
	}
	
	public int getRunningTasks() {
		return this.runningTasks;
	}
	
	@Override
	public boolean isDisposed() {
		return this.disposed;
	}
}
