/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// ObjectResolver.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 4:14:19 PM
////////

package me.corsin.javatools.dynamictext;

public interface ObjectResolver {
	
	Object resolveForObjectAndContext(Object self, Context context);

}
