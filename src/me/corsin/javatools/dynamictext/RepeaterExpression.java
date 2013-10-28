/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// RepeatorExpression.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:54:02 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;

import me.corsin.javatools.string.TextParser;

public class RepeaterExpression implements Expression {

	////////////////////////
	// VARIABLES
	////////////////
	
	private Expression repeatBlock;
	private ObjectResolver variableExpression;
	private String variableToSetName;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public RepeaterExpression(String repeatBlock) throws IOException {
		TextParser parser = new TextParser(repeatBlock);
		parser.readIgnore();
		
		String variableExpressionStr = parser.readUpTo("-");
		this.variableExpression = new ContextObjectResolver(variableExpressionStr.trim());
		
		parser.read('-');
		parser.read('>');
		
		parser.readIgnore();
		
		String identifier = parser.readIdentifier();
		this.variableToSetName = identifier;
		parser.readIgnore();
		parser.read(':');
		this.repeatBlock = new TextCompilationUnit(parser.readToEnd());
	}

	////////////////////////
	// METHODS
	////////////////
	
	private void renderLine(Object object, Context context, int lineNumber, StringBuilder output) {
		String lineNumberInput = "#" + this.variableToSetName + "Number";
		context.put(lineNumberInput, lineNumber);
		context.put(this.variableToSetName, object);

		output.append(this.repeatBlock.renderForContext(context));
		
		context.remove(lineNumberInput);
		context.remove(this.variableToSetName);
	}
	
	@Override
	public String renderForContext(Context context) {
		if (context == null) {
			return "RepeaterExpression:{No context set}";
		}
		
		
		Object collection = this.variableExpression.resolveForObjectAndContext(null, context);
		
		if (collection == null) {
			return "";
		}
		
		context.put("#" + this.variableToSetName, collection);
		
		StringBuilder output = new StringBuilder();
		if (collection instanceof Object[]) {
			Object[] array = (Object[])collection;
			
			for (int i = 0, length = array.length; i < length; i++){
				Object obj = array[i];
				this.renderLine(obj, context, i, output);
			}
			
		} else if (collection instanceof Iterable) {
			Iterable<?> iterable = (Iterable<?>)collection;
			
			int i = 0;
			for (Object obj : iterable) {
				this.renderLine(obj, context, i, output);
				i++;
			}
		} else {
			output.append("RepeaterExpression:{Expects an Array or an Iterable object}");
		}
		
		context.remove("#" + this.variableToSetName);
		
		return output.toString();
	}
	
	public String toString() {
		return this.renderForContext(null);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
