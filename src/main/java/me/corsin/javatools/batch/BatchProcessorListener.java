/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.batch
// BatchProcessorListener.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on May 12, 2014 at 3:29:37 PM
////////

package me.corsin.javatools.batch;

import java.util.List;

public interface BatchProcessorListener<T> {
	
	void handleBatchedEntities(BatchProcessor<T> batchProcessor, List<T> batchedEntities);

}
