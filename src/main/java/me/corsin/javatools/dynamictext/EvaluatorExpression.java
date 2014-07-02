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

	public Object getResultForContext(Context context) {
		if (context == null) {
			return null;
		}

		Object object = this.contextObjectResolver.resolveForObjectAndContext(null, context);

		if (object == null) {
			return null;
		}

		return object;
	}

	@Override
	public String renderForContext(Context context) {
		Object object = this.getResultForContext(context);

		return object != null ? object.toString() : "";
	}

	@Override
	public String toString() {
		return this.renderForContext(null);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
