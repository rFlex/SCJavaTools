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
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import me.corsin.javatools.misc.Disposable;

public class TaskQueue implements Disposable {

	////////////////////////
	// VARIABLES
	////////////////
	
	final protected List<Task> pendingTasks;
	final private Queue<Task> tasks;
	private int id;
	private boolean disposed;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TaskQueue() {
		this.tasks = new ArrayDeque<Task>();
		this.pendingTasks = new ArrayList<Task>();
	}

	////////////////////////
	// METHODS
	////////////////
	
	public void flushTasks() {
		synchronized (this) {
			synchronized (this.pendingTasks) {
				for (int i = 0, size = this.pendingTasks.size(); i < size; i++) {
					this.tasks.add(this.pendingTasks.get(i));
				}
				this.pendingTasks.clear();
			}
			
			while (!this.tasks.isEmpty() && !this.disposed) {
				final Task task = this.tasks.poll();
				
				task.run();
				
				synchronized (task) {
					task.notifyAll();
				}
			}
			
			this.notifyAll();
		}
	}
	
	@Override
	public void dispose() {
		this.disposed = true;
	}
	
	public void executeAsync(Task task) {
		synchronized (this.pendingTasks) {
			this.pendingTasks.add(task);
			this.pendingTasks.notifyAll();
		}
	}
	
	public void executeSync(Task task) {
		this.executeAsync(task);
		task.waitCompletion();
	}
	
	public void executeSyncTimed(Task task, long inMs) {
		try {
			Thread.sleep(inMs);
			this.executeSync(task);
		} catch (InterruptedException e) {
			
		}
	}
	
	public void executeAsyncTimed(Task task, long inMs) {
		final Task theTask = task;
		
		// This implementation is not really suitable for now as the timer uses its own thread
		// The TaskQueue itself should be able in the future to handle this without using a new thread
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				executeAsync(theTask);
			}
		}, inMs);
	}
	
	public void waitAllTasks() {
		synchronized (this) {
			boolean hasTaskPending = false;
			synchronized (this.pendingTasks) {
				hasTaskPending = this.hasTaskPending();
			}
			
			if (hasTaskPending) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public boolean hasTaskPending() {
		return !this.pendingTasks.isEmpty();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean isDisposed() {
		return this.disposed;
	}
}
