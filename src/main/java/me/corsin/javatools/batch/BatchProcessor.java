/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.task
// BatchProcessor.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on May 12, 2014 at 3:29:07 PM
////////

package me.corsin.javatools.batch;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import me.corsin.javatools.timer.TimeSpan;

public class BatchProcessor<T> implements Runnable, Closeable, Flushable {

	////////////////////////
	// VARIABLES
	////////////////

	private Queue<T> batchedEntities;
	private boolean closed;
	private int maxBatchSize;
	private int minBatchSize;
	private TimeSpan maxBatchInterval;
	private BatchProcessorListener<T> listener;
	private boolean processing;
	private Object userInfo;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////

	public BatchProcessor() {
		this.batchedEntities = new ArrayDeque<T>();
		new Thread(this).start();
	}

	////////////////////////
	// METHODS
	////////////////

	public void waitCompletion() {
		synchronized (this.batchedEntities) {
			while ((this.processing || this.needsBatch()) && !this.closed) {
				try {
					this.batchedEntities.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}

	@Override
	public void flush() {
		synchronized (this.batchedEntities) {
			this.batchedEntities.notify();
		}
		this.waitCompletion();
	}

	public void run() {
		List<T> entityToHandle = new ArrayList<T>();
		while (!this.closed) {
			entityToHandle.clear();
			
			synchronized (this.batchedEntities) {
				int maxBatchSize = this.maxBatchSize;
				for (int i = 0; i < maxBatchSize && !this.batchedEntities.isEmpty(); i++) {
					entityToHandle.add(this.batchedEntities.poll());
				}
				this.processing = true;
			}
			
			if (!entityToHandle.isEmpty()) {
				BatchProcessorListener<T> listener = this.listener;
				if (listener != null) {
					listener.handleBatchedEntities(this, entityToHandle);
				}				
			}
			
			synchronized (this.batchedEntities) {
				while (!this.needsBatch() && !this.closed) {
					this.processing = false;
					this.batchedEntities.notify();
					try {
						TimeSpan maxBatchInterval = this.maxBatchInterval;
						
						if (maxBatchInterval == null) {
							this.batchedEntities.wait();
						} else {
							long timeout = maxBatchInterval.getTotalMs();
							this.batchedEntities.wait(timeout);
						}
					} catch (InterruptedException e) {
						
					}
				}
			}
		}
	}
		
	public void addBatchEntity(T entity) {
		synchronized (this.batchedEntities) {
			this.batchedEntities.add(entity);
			
			if (this.needsBatch()) {
				this.batchedEntities.notify();
			}
		}
	}
	
	@Override
	public void close() {
		this.closed = true;
		synchronized (this.batchedEntities) {
			this.batchedEntities.notify();
		}
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	private boolean needsBatch() {
		return this.batchedEntities.size() > this.minBatchSize;
	}
	
	public TimeSpan getMaxBatchInterval() {
		return maxBatchInterval;
	}
	
	public void setMaxBatchInterval(TimeSpan maxBatchInterval) {
		this.maxBatchInterval = maxBatchInterval;
	}

	public BatchProcessorListener<T> getListener() {
		return listener;
	}

	public void setListener(BatchProcessorListener<T> listener) {
		this.listener = listener;
	}

	public int getMaxBatchSize() {
		return maxBatchSize;
	}

	public void setMaxBatchSize(int maxBatchSize) {
		this.maxBatchSize = maxBatchSize;
	}
	
	public int getMinBatchSize() {
		return minBatchSize;
	}

	public void setMinBatchSize(int minBatchSize) {
		this.minBatchSize = minBatchSize;
	}

	public boolean isProcessing() {
		return this.processing;
	}

	public Object getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}

}
