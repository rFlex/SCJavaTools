/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// ByteArrayResponseTransformer.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on May 12, 2014 at 2:10:56 PM
////////

package me.corsin.javatools.http;

import java.io.IOException;
import java.io.InputStream;

import me.corsin.javatools.http.APICommunicator.IResponseTransformer;
import me.corsin.javatools.io.IOUtils;

public class ByteArrayResponseTransformer implements IResponseTransformer {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ByteArrayResponseTransformer() {

	}

	////////////////////////
	// METHODS
	////////////////

	@Override
	public Object transformResponse(InputStream inputStream, Class<?> expectedOutputResponse) throws IOException {
		return IOUtils.readStream(inputStream);
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
