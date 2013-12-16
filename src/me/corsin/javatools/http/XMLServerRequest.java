/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// XMLServerRequest.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 1:20:16 PM
////////

package me.corsin.javatools.http;

/**
 * This class uses the SharedProperties to get the information about which ResponseTransformer it should use to deserialize
 * the xml content into POJO
 * You must either set XMLResponseTransformer with an instance of the IResponseTransformer or set XMLResponseTransformerClass
 * with the complete name of the class that implements IResponseTransformer.
 * @author simoncorsin
 *
 */
public class XMLServerRequest extends ServerRequest {

	////////////////////////
	// VARIABLES
	////////////////
	
	public static final String ResponseTransformerSharedPropertiesKey = "XMLResponseTransformer";
	public static final String ResponseTransformerClassSharedPropertiesKey = "XMLResponseTransformerClass";

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public XMLServerRequest(String url) {
		this(null, url);
	}
	
	public XMLServerRequest(Class<?> expectedResponseNodeType, String url) {
		this(expectedResponseNodeType, url, HttpMethod.GET);
	}
	
	public XMLServerRequest(String url, HttpMethod httpMethod) {
		this(null, url, httpMethod);
	}
	
	public XMLServerRequest(Class<?> expectedResponseNodeType, String url, HttpMethod requestType) {
		super(expectedResponseNodeType, url, requestType);
		
		this.configureResponseTransformerUsingSharedProperties(ResponseTransformerSharedPropertiesKey, ResponseTransformerClassSharedPropertiesKey);
	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
