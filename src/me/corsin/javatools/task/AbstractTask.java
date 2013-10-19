/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// Task.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 7:33:29 PM
////////

package me.corsin.javatools.task;

public abstract class AbstractTask<TaskType, TaskListenerType extends ITaskListener<TaskType>> implements Runnable {
	
	////////////////////////
	// VARIABLES
	////////////////
	
	private Throwable thrownException;
	private boolean completed;
	private boolean canceled;
	private TaskListenerType listener;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public AbstractTask() {
	}

	////////////////////////
	// METHODS
	////////////////
	
	protected abstract void handle() throws Throwable;
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			if (this.canceled) {
				throw new Exception("Task cancelled");
			}
			
			this.handle();
			
			synchronized (this) {
				this.completed = true;
			}
		} catch (Throwable e) {
			this.thrownException = e;
		}
		if (this.listener != null) {
			this.listener.onCompleted((TaskType)this);
		}
	}
	
	public void waitCompletion() {
		if (!this.completed) {
			try {
				synchronized (this) {
					if (!this.completed) {
						this.wait();
					}
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cancel() {
		this.canceled = true;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public Throwable getThrownException() {
		this.waitCompletion();
		return thrownException;
	}

	public boolean isCompleted() {
		return completed;
	}

	public TaskListenerType getListener() {
		return listener;
	}

	public void setListener(TaskListenerType listener) {
		this.listener = listener;
	}
}
