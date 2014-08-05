/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// CommunicatorResponse.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 5, 2014 at 4:23:05 PM
////////

package me.corsin.javatools.http;

public class CommunicatorResponse<T, T2> {

	////////////////////////
	// VARIABLES
	////////////////

	private T successObjectResponse;
	private T2 failureObjectResponse;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public CommunicatorResponse() {

	}

	////////////////////////
	// METHODS
	////////////////

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public T getSuccessObjectResponse() {
		return this.successObjectResponse;
	}

	public T2 getFailureObjectResponse() {
		return this.failureObjectResponse;
	}

	public void setSuccessObjectResponse(T successObjectResponse) {
		this.successObjectResponse = successObjectResponse;
	}

	public void setFailureObjectResponse(T2 failureObjectResponse) {
		this.failureObjectResponse = failureObjectResponse;
	}
}
