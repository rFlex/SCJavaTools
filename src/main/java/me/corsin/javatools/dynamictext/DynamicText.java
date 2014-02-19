/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// DynamicText.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 2:26:23 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.File;
import java.io.IOException;

import me.corsin.javatools.io.IOUtils;

public class DynamicText {

	////////////////////////
	// VARIABLES
	////////////////

	private TextCompilationUnit compilationUnit;
	private Context context;
	private String text;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public DynamicText(File file) throws IOException {
		this(IOUtils.readFileAsString(file));
	}
	
	public DynamicText() {
		this("");
	}
	
	public DynamicText(String text) {
		this(text, new Context());
	}
	
	public DynamicText(Context context) {
		this("", context);
	}
	
	public DynamicText(String text, Context context) {
		this(new TextCompilationUnit(text), context);
	}
	
	public DynamicText(TextCompilationUnit compiledText, Context context) {
		this.context = context;
		this.compilationUnit = compiledText;
		
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
	
	public void put(String key, Object value) {
		this.context.put(key, value);
	}
	
	public void remove(String key) {
		this.context.remove(key);
	}
	
	public String toString() {
		if (this.context == null) {
			return "DynamicText:{No context set}";
		}
		
		return this.compilationUnit.renderForContext(this.context);
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
		return this.text;
	}

	public void setText(String text) {
		if (text == null) {
			text = "";
		}

		this.compilationUnit.compile(text);
		this.text = text;
	}

	public TextCompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	public void setCompilationUnit(TextCompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}
}
