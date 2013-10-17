/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// JsonJsonServerRequest.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 1:00:27 PM
////////

package me.corsin.javatools.http;

/**
 * This class uses the SharedProperties to get the information about which ResponseTransformer it should use to deserialize
 * the json content into POJO
 * You must either set JsonResponseTransformer with an instance of the IResponseTransformer or set JsonResponseTransformerClass
 * with the complete name of the class that implements IResponseTransformer.
 * @author simoncorsin
 *
 */
public class JsonServerRequest extends ServerRequest {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public JsonServerRequest(String url) {
		this(null, url);
	}
	
	public JsonServerRequest(Class<?> expectedResponseNodeType, String url) {
		this(expectedResponseNodeType, url, HttpMethod.GET);
	}
	
	public JsonServerRequest(String url, HttpMethod httpMethod) {
		this(null, url, httpMethod);
	}
	
	public JsonServerRequest(Class<?> expectedResponseNodeType, String url, HttpMethod requestType) {
		super(expectedResponseNodeType, url, requestType);
		
		this.configureResponseTransformerUsingSharedProperties("JsonResponseTransformer");
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
