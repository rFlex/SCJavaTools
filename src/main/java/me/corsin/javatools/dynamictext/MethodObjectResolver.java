/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.dynamictext
// MethodObjectResolver.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 5:05:14 PM
////////

package me.corsin.javatools.dynamictext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.reflect.ReflectionUtils;
import me.corsin.javatools.string.TextParser;

public class MethodObjectResolver implements ObjectResolver {

	////////////////////////
	// VARIABLES
	////////////////
	
	private List<ObjectResolver> parametersResolvers;
	private String methodName;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public MethodObjectResolver(String methodName, String args) throws IOException {
		this.methodName = methodName;
		
		if (args.length() > 0) {
			TextParser parser = new TextParser(args);
			this.parametersResolvers = new ArrayList<ObjectResolver>();

			boolean first = true;
			
			while (!parser.isEmpty()) {
				parser.readIgnore();
				
				if (!first) {
					parser.read(',');
				}
				first = false;
				parser.readIgnore();
				
				this.parametersResolvers.add(new ContextObjectResolver(parser));
			}
		}
	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public Object resolveForObjectAndContext(Object self, Context context) {
		Object ret = null;
		
		if (parametersResolvers != null) {
			Object[] parameters = new Object[this.parametersResolvers.size()];
			for (int i = 0, length = parameters.length; i < length; i++) {
				parameters[i] = parametersResolvers.get(i).resolveForObjectAndContext(null, context);
			}
			
			ret = ReflectionUtils.invoke(self, this.methodName, parameters);
		} else {
			ret = ReflectionUtils.invoke(self, this.methodName);
		}
		
		return ret;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
