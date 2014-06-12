/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// StringResponseTransformer.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 11, 2014 at 3:10:56 PM
////////

package me.corsin.javatools.http;

import java.io.IOException;
import java.io.InputStream;

import me.corsin.javatools.http.APICommunicator.IResponseTransformer;
import me.corsin.javatools.io.IOUtils;

public class StringResponseTransformer implements IResponseTransformer {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public StringResponseTransformer() {

	}

	////////////////////////
	// METHODS
	////////////////
	
	@Override
	public Object transformResponse(InputStream inputStream, Class<?> expectedOutputResponse) throws IOException {
		return IOUtils.readStreamAsString(inputStream);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
