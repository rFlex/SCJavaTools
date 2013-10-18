/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// DynamicText.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:26:23 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.string.TextParser;

public class DynamicText implements Expression {

	////////////////////////
	// VARIABLES
	////////////////

	private String text;
	private List<Expression> expressions;
	private Context context;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public DynamicText() {
		this("");
	}
	
	public DynamicText(String text) {
		this(text, new Context());
	}
	
	public DynamicText(String text, Context context) {
		this.expressions = new ArrayList<Expression>();
		this.context = context;
		this.setText(text);
		
		if (context != null) {
			this.initializeBaseExpressions(context);
		}
	}

	////////////////////////
	// METHODS
	////////////////
	
	private void initializeBaseExpressions(Context context) {
		context.put("#if", new If());
	}
	
	private void clear() {
		this.expressions.clear();
	}
	
	public void put(String key, Object value) {
		this.context.put(key, value);
	}
	
	public void remove(String key) {
		this.context.remove(key);
	}
	
	private void parseString(String text) {
		try {
			this.clear();
			
			TextParser parser = new TextParser(text);
			
			while (!parser.isEmpty()) {
				String textExpression = parser.readUpTo("{[");
				
				if (!textExpression.isEmpty()) {
					this.expressions.add(new TextExpression(textExpression));
				}
				
				if (!parser.isEmpty()) {
					char c = parser.readChar();
					
					switch (c) {
					case '{':
						String evalExpression = parser.readScope('{', '}');
						this.expressions.add(new EvaluatorExpression(evalExpression));
						break;
					case '[':
						String repeatExpression = parser.readScope('[', ']');
						this.expressions.add(new RepeaterExpression(repeatExpression));
						break;
					}	
				}
			}
			
			
		} catch (IOException e) {
			throw new InvalidTextException(e.getMessage(), e);
		}
	}
	
	public String toString() {
		if (this.context == null) {
			return "DynamicText:{No context set}";
		}
		
		return this.renderForContext(this.context);
	}
	
	@Override
	public String renderForContext(Context context) {
		StringBuilder sb = new StringBuilder();
		
		for (Expression expression : this.expressions) {
			sb.append(expression.renderForContext(context));
		}
		
		return sb.toString();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public Context getContext() {
		return this.context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) {
			text = "";
		}
		this.text = text;
		this.parseString(text);
	}
}
