/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.cache
// DataCacheRefresher.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 8, 2014 at 4:12:35 PM
////////

package me.corsin.javatools.cache;

public interface CacheEntryRefresher<T> {

	T refreshData() throws Exception;

}
