/////////////////////////////////////////////////
// Project : exiled-api
// Package : com.kerious.exiled.masterserver.api.communicator
// ServerRequest.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 24, 2013 at 5:03:01 PM
////////

package me.corsin.javatools.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.corsin.javatools.http.APICommunicator.IResponseTransformer;

public class ServerRequest {

	////////////////////////
	// VARIABLES
	////////////////
	
	final private List<Parameter> parameters;
	final private Map<String, String> headers;
	private InputStream body;
	private String contentType;
	private long contentLength;
	private String url;
	private URL generatedURL;
	private HttpMethod httpMethod;
	private Class<?> expectedResponseType;
	private IResponseTransformer responseTransformer;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public ServerRequest(String url) {
		this(null, url);
	}
	
	public ServerRequest(Class<?> expectedResponseNodeType, String url) {
		this(expectedResponseNodeType, url, HttpMethod.GET);
	}
	
	public ServerRequest(String url, HttpMethod httpMethod) {
		this(null, url, httpMethod);
	}
	
	public ServerRequest(Class<?> expectedResponseNodeType, String url, HttpMethod requestType) {
		this.url = url;
		this.parameters = new ArrayList<Parameter>();
		this.headers = new HashMap<String, String>();
		this.httpMethod = requestType;
		
		this.expectedResponseType = expectedResponseNodeType;
	}

	////////////////////////
	// METHODS
	////////////////

	public static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String toString() {
		String finalUrl = url;

		if (!this.parameters.isEmpty()) {
			finalUrl += "?";
			
			boolean first = true;
			for (Parameter parameter : this.parameters) {
				if (!first) {
					finalUrl += "&";
				}
				try {
					finalUrl += parameter.getName() + "="  + URLEncoder.encode(parameter.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				first = false;
			}
		}

		return finalUrl;
	}
	
	public String parametersToString() {
		StringBuilder parametersString = new StringBuilder();

		if (!this.parameters.isEmpty()) {
			boolean first = true;
			for (Parameter parameter : this.parameters) {
				if (!first) {
					parametersString.append('&');
				}
				try {
					parametersString.append(parameter.getName());
					parametersString.append('=');
					parametersString.append(URLEncoder.encode(parameter.getValue(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				first = false;
			}
		}

		return parametersString.toString();
	}
	
	public void addParameter(String name, long value) {
		this.addParameter(new Parameter(name, Long.toString(value)));
	}
	
	public void addParameter(String name, String value) {
		this.addParameter(new Parameter(name, value));
	}
	
	public void addParameter(Parameter parameter) {
		this.parameters.add(parameter);
	}
	
	public void addModule(long id) {
		this.addModule(Long.toString(id));
	}
	
	public void addModule(String module) {
		this.url += "/" + module;
	}
	
	public void generate() throws MalformedURLException {
		switch (this.getHttpMethod()) {
		case POST:
		case PUT:
			this.generatedURL = new URL(this.getURL());
			this.contentType = "application/x-www-form-urlencoded";
			
			try {
				String content = this.parametersToString();
				byte[] contentBytes = content.getBytes("UTF-8");
				ByteArrayInputStream bais = new ByteArrayInputStream(contentBytes);
				this.body = bais;
				this.contentLength = contentBytes.length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			this.headers.put("Content-Type", this.contentType);
			this.headers.put("Content-Length", Long.toString(this.contentLength));
			
			break;
			
		case DELETE:
		case GET:
			if (this.parameters.size() > 0) {
				this.generatedURL = new URL(this.getURL() + "?" + this.parametersToString());
			} else {
				this.generatedURL = new URL(this.getURL());
			}
			break;
		default:
			break;
		}
	}
	
	public Object getResponse() throws Throwable {
		return this.getResponse(this.expectedResponseType);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getResponse(Class<T> responseNodeType) throws Throwable {
		this.expectedResponseType = responseNodeType;
		
		APICommunicator communicator = new APICommunicator();
		
		communicator.setResponseTransformer(this.getResponseTransformer());
		
		return (T)communicator.getResponse(this);
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final String getURL() {
		return this.url;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public URL getGeneratedURL() {
		return this.generatedURL;
	}
	
	public final HttpMethod getHttpMethod() {
		return httpMethod;
	}
	
	public final void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Class<?> getExpectedResponseType() {
		return expectedResponseType;
	}
	
	public void setExpectedResponseType(Class<?> responseType) {
		this.expectedResponseType = responseType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public List<Parameter> getParameters() {
		return this.parameters;
	}
	
	public InputStream getBody() {
		return this.body;
	}
	
	public String getContentType() {
		return contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public IResponseTransformer getResponseTransformer() {
		return responseTransformer;
	}

	public void setResponseTransformer(IResponseTransformer responseTransformer) {
		this.responseTransformer = responseTransformer;
	}
}
