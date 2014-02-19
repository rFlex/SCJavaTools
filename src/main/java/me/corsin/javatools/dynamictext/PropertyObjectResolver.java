/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// PropertyObjectResolver.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 5:03:59 PM
////////

package me.corsin.javatools.dynamictext;

import me.corsin.javatools.reflect.ReflectionUtils;

public class PropertyObjectResolver implements ObjectResolver {

	////////////////////////
	// VARIABLES
	////////////////

	private String propertyName;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public PropertyObjectResolver(String propertyName) {
		this.propertyName = propertyName;
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public Object resolveForObjectAndContext(Object self, Context context) {
		return ReflectionUtils.getField(self, this.propertyName);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
