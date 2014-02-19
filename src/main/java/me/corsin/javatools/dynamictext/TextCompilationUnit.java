/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// CompiledText.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 27, 2013 at 4:47:18 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.io.IOUtils;
import me.corsin.javatools.string.TextParser;

public class TextCompilationUnit implements Expression {

	////////////////////////
	// VARIABLES
	////////////////
	
	final private List<Expression> expressions;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TextCompilationUnit() {
		this.expressions = new ArrayList<Expression>();
	}
	
	public TextCompilationUnit(String text) {
		this();
		this.compile(text);
	}

	////////////////////////
	// METHODS
	////////////////

	public void compile(InputStream inputStream) throws InvalidTextException, IOException {
		this.compile(IOUtils.readStreamAsString(inputStream));
	}
	
	public void compile(File fileInput) throws InvalidTextException, IOException {
		this.compile(IOUtils.readFileAsString(fileInput));
	}
	
	public void compile(String text) throws InvalidTextException {
		this.expressions.clear();
		try {
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
}
