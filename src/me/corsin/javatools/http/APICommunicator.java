/////////////////////////////////////////////////
// Project : exiled-api
// Package : com.kerious.exiled.masterserver.api.communicator
// APICommunicator.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 24, 2013 at 4:59:15 PM
////////

package me.corsin.javatools.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import me.corsin.javatools.io.IOUtils;

public class APICommunicator {
	
	public static interface IResponseTransformer {
		Object transformResponse(InputStream inputStream, Class<?> expectedOutputResponse) throws IOException;
	}
	
	////////////////////////
	// VARIABLES
	////////////////

	private IResponseTransformer responseTransformer;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public APICommunicator() {
		this.setResponseTransformer(new IResponseTransformer() {
			
			@Override
			public Object transformResponse(InputStream inputStream, Class<?> expectedOutputResponse) throws IOException {
				return IOUtils.readStreamAsString(inputStream);
			}
		});
	}
	
	////////////////////////
	// METHODS
	////////////////
	
	public Object getResponse(ServerRequest request) throws Exception {
		request.generate();
		
		return this.getResponse(request.getGeneratedURL(), request.getHttpMethod().toString(), request.getHeaders(), request.getBody(), request.getExpectedResponseType());
	}
	
	public Object getResponse(String url) throws Throwable {
		return this.getResponse(url, "GET", null, null);
	}
	
	public Object getResponse(String url, String httpMethod, Map<String, String> headers, InputStream body) throws Throwable {
		return this.getResponse(new URL(url), httpMethod, headers, body, null);
	}
	
	public Object getResponse(URL url, String httpMethod, Map<String, String> headers, InputStream body, Class<?> expectedResponseType) throws IOException {
		Object response = null;
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		try {
			connection.setRequestMethod(httpMethod);
			
			if (headers != null) {
				for (Entry<String, String> header : headers.entrySet()) {
					connection.setRequestProperty(header.getKey(), header.getValue());
				}
			}
			
			connection.setDoInput(true);
			
			if (body != null) {
				connection.setDoOutput(true);
				IOUtils.writeStream(connection.getOutputStream(), body);
				connection.getOutputStream().close();
			}
			
			InputStream toReceive = null;
			
			try {
				toReceive = connection.getInputStream();
			} catch (IOException e) {
				toReceive = connection.getErrorStream();
			}
			
			if (this.getResponseTransformer() != null) {
				response = this.getResponseTransformer().transformResponse(toReceive, expectedResponseType);
			} else {
				response = IOUtils.readStreamAsString(toReceive);
			}
			
			toReceive.close();
			
		} finally {
			connection.disconnect();
		}
		
		return response;
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public IResponseTransformer getResponseTransformer() {
		return responseTransformer;
	}

	public void setResponseTransformer(IResponseTransformer responseTransformer) {
		this.responseTransformer = responseTransformer;
	}
}
