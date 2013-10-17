/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// ConditionalObjectResolver.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 7:44:10 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;

import me.corsin.javatools.string.TextParser;

public class ConditionalObjectResolver implements ObjectResolver {

	////////////////////////
	// VARIABLES
	////////////////
	
	private boolean neg;
	private ObjectResolver conditionalResolver;
	private Expression subExpression;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public ConditionalObjectResolver(String block) throws IOException {
		this(new TextParser(block));
	}
	
	public ConditionalObjectResolver(TextParser parser) throws IOException {
		parser.readIgnore();
		
		if (parser.tryRead('!')) {
			this.neg = true;
			parser.readIgnore();
		}
		
		this.conditionalResolver = new ContextObjectResolver(parser);
		
		parser.readIgnore();
		parser.read(':');
		this.subExpression = new DynamicText(parser.readToEnd(), null);
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public Object resolveForObjectAndContext(Object self, Context context) {
		Object result = this.conditionalResolver.resolveForObjectAndContext(self, context);
		
		if (result == null) {
			return "";
		}
		
		try {
			boolean boolResult = (Boolean)result;
			
			if (boolResult ^ this.neg) {
				return this.subExpression.renderForContext(context);
			} else {
				return "";
			}
		} catch (Throwable t) {
			throw new InvalidTextException("A conditional must resolve to a boolean", t);
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
