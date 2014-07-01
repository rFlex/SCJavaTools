/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// ContextObjectResolver.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 4:01:49 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.string.TextParser;

public class ContextObjectResolver implements ObjectResolver {

	////////////////////////
	// VARIABLES
	////////////////

	private String contextVariableName;
	private List<ObjectResolver> subResolvers;
	private Object valueConstant;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ContextObjectResolver(String block) throws IOException {
		this(new TextParser(block));
	}

	public ContextObjectResolver(TextParser parser) throws IOException {
		String variableName = parser.readUpTo(".,():").trim();

		if (variableName.length() == 0) {
			throw new InvalidTextException("Empty identifier");
		}

		this.contextVariableName = variableName;

		if (this.contextVariableName.startsWith("\"") && this.contextVariableName.endsWith("\"")) {
			if (this.contextVariableName.length() < 2) {
				throw new InvalidTextException("String identifier not terminated");
			}
			this.valueConstant = this.contextVariableName.substring(1, this.contextVariableName.length() - 1);
		} else {
			try {
				this.valueConstant = Integer.parseInt(variableName);
			} catch (NumberFormatException e) {

			}
		}

		while (!parser.isEmpty() && parser.tryRead('.')) {
			String identifier = parser.readUpTo(".,():").trim();

			if (identifier.length() == 0) {
				throw new InvalidTextException("Empty identifier after a '.'");
			}

			ObjectResolver newResolver = null;

			if (!parser.isEmpty()) {
				char c = parser.readChar();

				switch (c) {
				case '(':
					String parameters = parser.readScope('(', ')').trim();
					newResolver = new MethodObjectResolver(identifier, parameters);
					break;
				case ':':
				case ',':
				case ')':
				case '.':
					newResolver = new PropertyObjectResolver(identifier);
					parser.back();
					break;
				}
			} else {
				newResolver = new PropertyObjectResolver(identifier);
			}

			if (this.subResolvers == null) {
				this.subResolvers = new ArrayList<ObjectResolver>();
			}
			this.subResolvers.add(newResolver);
		}
	}

	////////////////////////
	// METHODS
	////////////////

	@Override
	public Object resolveForObjectAndContext(Object self, Context context) {
		Object resolved = null;

		if (this.valueConstant != null) {
			resolved = this.valueConstant;
		} else {
			resolved = context.get(this.contextVariableName);
		}

		if (this.subResolvers != null) {
			for (ObjectResolver resolver : this.subResolvers) {
				if (resolved == null) {
					break;
				}
				resolved = resolver.resolveForObjectAndContext(resolved, context);
			}
		}

		return resolved;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
