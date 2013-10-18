/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// Task.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 7:33:29 PM
////////

package me.corsin.javatools.task;

public abstract class Task implements Runnable {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Throwable thrownException;
	private boolean completed;
	private boolean canceled;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public Task() {
	}

	////////////////////////
	// METHODS
	////////////////
	
	protected abstract void doTask() throws Throwable;
	
	@Override
	public void run() {
		try {
			if (this.canceled) {
				throw new Exception("Task cancelled");
			}
			
			this.doTask();
			
			synchronized (this) {
				this.completed = true;
			}
		} catch (Throwable e) {
			this.thrownException = e;
		}
	}
	
	public void waitCompletion() {
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
	
	public void cancel() {
		this.canceled = true;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public Throwable getThrownException() {
		return thrownException;
	}

	public boolean isCompleted() {
		return completed;
	}
}
