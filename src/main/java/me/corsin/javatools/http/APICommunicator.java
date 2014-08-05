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

@SuppressWarnings("rawtypes")
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

	public CommunicatorResponse getResponse(ServerRequest request) throws IOException {
		request.generate();

		Class<?> failureType = request.getFailureResponseType();

		if (failureType == null) {
			failureType = request.getExpectedResponseType();
		}

		return this.getResponse(request.getGeneratedURL(), request.getHttpMethod().toString(), request.getHeaders(), request.getBody(), request.getExpectedResponseType(), failureType);
	}

	public CommunicatorResponse getResponse(String url) throws Throwable {
		return this.getResponse(url, "GET", null, null);
	}

	public CommunicatorResponse getResponse(String url, String httpMethod, Map<String, String> headers, InputStream body) throws Throwable {
		return this.getResponse(new URL(url), httpMethod, headers, body, null, null);
	}

	public CommunicatorResponse getResponse(URL url, String httpMethod, Map<String, String> headers, InputStream body, Class<?> expectedResponseType, Class<?> failedResponseType) throws IOException {
		CommunicatorResponse response = new CommunicatorResponse();

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
			boolean isFailure = false;

			try {
				toReceive = connection.getInputStream();
			} catch (IOException e) {
				toReceive = connection.getErrorStream();
				isFailure = true;

				if (toReceive == null) {
					throw e;
				}
			}

			Object rawResponseObject = null;
			if (this.getResponseTransformer() != null) {
				rawResponseObject = this.getResponseTransformer().transformResponse(toReceive, isFailure ? failedResponseType : expectedResponseType);
			} else {
				rawResponseObject = IOUtils.readStreamAsString(toReceive);
			}
			if (isFailure) {
				response.setFailureObjectResponse(rawResponseObject);
			} else {
				response.setSuccessObjectResponse(rawResponseObject);
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
		return this.responseTransformer;
	}

	public void setResponseTransformer(IResponseTransformer responseTransformer) {
		this.responseTransformer = responseTransformer;
	}
}
