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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import me.corsin.javatools.timer.TimeSpan;

public class BatchProcessor<T> implements Runnable, Closeable {

	////////////////////////
	// VARIABLES
	////////////////

	private Queue<T> batchedEntities;
	private boolean closed;
	private int maxBatchSize;
	private TimeSpan maxBatchInterval;
	private BatchProcessorListener<T> listener;
	
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

	public void run() {
		List<T> entityToHandle = new ArrayList<T>();
		while (!this.closed) {
			entityToHandle.clear();
			
			synchronized (this.batchedEntities) {
				int maxBatchSize = this.maxBatchSize;
				for (int i = 0; i < maxBatchSize && !this.batchedEntities.isEmpty(); i++) {
					entityToHandle.add(this.batchedEntities.poll());
				}
			}
			
			if (!entityToHandle.isEmpty()) {
				BatchProcessorListener<T> listener = this.listener;
				if (listener != null) {
					listener.handleBatchedEntities(this, entityToHandle);
				}				
			}
			
			synchronized (this.batchedEntities) {
				if (!this.needsBatch()) {
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
		return this.batchedEntities.size() >= this.maxBatchSize && !this.closed;
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
}
