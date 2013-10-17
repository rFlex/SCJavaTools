/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// EvaluatorExpression.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:52:56 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;

import me.corsin.javatools.string.TextParser;

public class EvaluatorExpression implements Expression {

	////////////////////////
	// VARIABLES
	////////////////
	
	private ObjectResolver contextObjectResolver;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public EvaluatorExpression(String evalBlock) throws IOException {
		TextParser parser = new TextParser(evalBlock);
		parser.readIgnore();
		
		if (parser.tryRead('?')) {
			this.contextObjectResolver = new ConditionalObjectResolver(parser);
		} else {
			this.contextObjectResolver = new ContextObjectResolver(parser);
		}
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public String renderForContext(Context context) {
		if (context == null) {
			return "EvaluatorExpression: {No context set}";
		}
		
		Object object = this.contextObjectResolver.resolveForObjectAndContext(null, context);
		
		if (object == null) {
			return "";
		}
		return object.toString();
	}
	
	@Override
	public String toString() {
		return this.renderForContext(null);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
