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
	private boolean started;
	private boolean canceled;
	private boolean listenerCalled;
	private TaskListenerType listener;
	private Object listenerCalledLock;
	private Object creator;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public AbstractTask() {
		this(null);
	}
	
	public AbstractTask(Object creator) {
		this.setCreator(creator);
		this.listenerCalledLock = new Object();
	}

	////////////////////////
	// METHODS
	////////////////
	
	protected abstract void handle() throws Throwable;
	
	@Override
	public void run() {
		this.started = true;
		this.listenerCalled = false;
		try {
			if (this.canceled) {
				throw new Exception("Task cancelled");
			}
			
			this.handle();
			
		} catch (Throwable e) {
			this.thrownException = e;
		}
		this.completed = true;
		this.callListener();
		this.started = false;
	}
	
	@SuppressWarnings("unchecked")
	private void callListener() {
		synchronized (this.listenerCalledLock) {
			if (!this.listenerCalled && this.getListener() != null) {
				this.listenerCalled = true;
				this.getListener().onCompleted(this.getCreator(), (TaskType)this);
			}
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

	public AbstractTask<TaskType, TaskListenerType> setListener(TaskListenerType listener) {
		this.listener = listener;
		
		if (this.started) {
			this.callListener();
		}
		
		return this;
	}

	public Object getCreator() {
		return creator;
	}

	public void setCreator(Object creator) {
		this.creator = creator;
	}
}
