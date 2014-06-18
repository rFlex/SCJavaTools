/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.string
// TextParser.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 3:15:30 PM
////////

package me.corsin.javatools.string;

import java.io.IOException;
import java.io.InputStream;

import me.corsin.javatools.dynamictext.InvalidTextException;
import me.corsin.javatools.io.IOUtils;

public class TextParser {

	////////////////////////
	// VARIABLES
	////////////////
	
	private char[] text;
	private int currentIndex;
	private boolean enableEscapeChar;
	private String ignoreStr;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TextParser(InputStream inputStream) throws IOException {
		this(IOUtils.readStreamAsString(inputStream));
	}
	
	public TextParser(String inputString) throws IOException {
		this.currentIndex = -1;
		this.text = inputString.toCharArray();
		this.enableEscapeChar(true);
		this.setIgnoreStr(" \t\n\r");
	}
	
	////////////////////////
	// METHODS
	////////////////
	
	public int save() {
		return this.currentIndex;
	}
	
	public void restore(int id) throws IOException {
		if (id > this.text.length || id < -1) {
			throw new IOException("Invalid id");
		}
		
		this.currentIndex = id;
	}
	
	private void ensureEOF() throws IOException {
		if (this.currentIndex >= this.text.length) {
			throw new IOException("EOF");
		}
		if (this.currentIndex < 0) {
			throw new IOException("No char read");
		}
	}
	
	public char readChar() throws IOException {
		this.currentIndex++;
		this.ensureEOF();
		
		return this.text[this.currentIndex];
	}
	
	public char peekChar() throws IOException {
		this.ensureEOF();
		
		return this.text[this.currentIndex];
	}
	
	public boolean tryRead(char c) throws IOException {
		char actualChar = this.readChar();
		
		if (c != actualChar) {
			this.back();
			return false;
		}
		
		return true;
	}
	
	public void read(char c) throws IOException {
		char actualChar = this.readChar();
		
		if (c != actualChar) {
			throw new IOException("Bad character [" + actualChar + "], expected [" + c + "]");
		}
	}
	
	public void read(String str) throws IOException {
		for (int i = 0, length = str.length(); i < length; i++) {
			this.read(str.charAt(i));
		}
	}
	
	public void readIgnore() throws IOException {
		this.readIgnore(this.getIgnoreStr());
	}
	
	public void readIgnore(String ignoreStr) throws IOException {
		while (!this.isEmpty()) {
			char c = this.readChar();
			
			if (!Strings.contains(ignoreStr, c)) {
				this.currentIndex--;
				break;
			}
		}
	}
	
	public String readScope(char scopeStartChar, char scopeEndChar) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		boolean endScopeFound = false;
		boolean shouldIgnoreCharInterpretation = false;
		boolean shouldWriteChar = false;
		
		int scopeLevel = 1;
		
		while (scopeLevel > 0) {
			char c = this.readChar();
			shouldWriteChar = true;
			
			if (shouldIgnoreCharInterpretation) {
				shouldIgnoreCharInterpretation = false;
			} else {
				if (c == scopeStartChar) {
					scopeLevel++;
				} else if (c == scopeEndChar) {
					scopeLevel--;
					
					if (scopeLevel == 0) {
						endScopeFound = true;
					}
				} else if (c == '\'') {
					shouldIgnoreCharInterpretation = true;
					shouldWriteChar = false;
				}
			}
			
			if (scopeLevel > 0 && shouldWriteChar) {
				sb.append(c);
			}
		}
		if (!endScopeFound) {
			throw new InvalidTextException("Scope unterminated (expected '" + scopeEndChar + "')", null);
		}
		
		return sb.toString();
	}
	
	public String readUpTo(String stopString) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		boolean shouldEscape = false;
		
		while (!this.isEmpty()) {
			char c = this.readChar();

			if (shouldEscape) {
				sb.append(c);
				shouldEscape = false;
			} else {
				if (c == '\'' && this.enableEscapeChar) {
					shouldEscape = true;
				} else {
					boolean isIgnore = Strings.contains(stopString, c);
					
					if (isIgnore) {
						this.currentIndex--;
						break;
					} else {
						sb.append(c);
					}
				}
			}
		}
		
		if (shouldEscape) {
			throw new IOException("Unterminated escape string");
		}
		
		return sb.toString();
	}
	
	public String readToEnd() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		while (!this.isEmpty()) {
			sb.append(this.readChar());
		}
		
		return sb.toString();
	}
	
	public String readIdentifier() throws IOException {
		StringBuilder sb = new StringBuilder();

		while (!this.isEmpty()) {
			char c = this.readChar();
			
			if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
				sb.append(c);
			} else {
				this.currentIndex--;
				break;
			}
		}
		
		String result = sb.toString();
		
		if (result.length() == 0) {
			throw new IOException("Unable to read identifier");
		}
		
		return result;
	}
	
	public void back() {
		this.currentIndex--;
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public int currentIndex() {
		return this.currentIndex;
	}
	
	public boolean isEmpty() {
		return this.currentIndex + 1 >= this.text.length;
	}

	public boolean isEnableEscapeChar() {
		return enableEscapeChar;
	}

	public void enableEscapeChar(boolean enable) {
		this.enableEscapeChar = enable;
	}

	public String getIgnoreStr() {
		return ignoreStr;
	}

	public void setIgnoreStr(String ignoreStr) {
		this.ignoreStr = ignoreStr;
	}
}
