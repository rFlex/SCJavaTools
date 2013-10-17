/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// TextExpression.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:34:06 PM
////////

package me.corsin.javatools.dynamictext;

public class TextExpression implements Expression {

	////////////////////////
	// VARIABLES
	////////////////

	private String block;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TextExpression(String block) {
		this.block = block;
	}

	////////////////////////
	// METHODS
	////////////////
	
	public String toString() {
		return "Text expression:{" + this.block + "}";
	}

	@Override
	public String renderForContext(Context context) {
		return this.block;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
