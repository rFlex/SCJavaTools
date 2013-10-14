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
import java.io.IOException;
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
	private boolean hasRawData;

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
					finalUrl += parameter.getName() + "="  + URLEncoder.encode(parameter.getValue().toString(), "UTF-8");
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
					parametersString.append(URLEncoder.encode(parameter.getValue().toString(), "UTF-8"));
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
	
	public void addParameter(String name, Object value, String fileName) {
		this.addParameter(new Parameter(name, value, fileName));
	}
	
	public void addParameter(Parameter parameter) {
		this.parameters.add(parameter);
		if (parameter.isRawData()) {
			this.hasRawData = true;
		}
	}
	
	public void generate() throws MalformedURLException {
		switch (this.getHttpMethod()) {
		case POST:
		case PUT:
			this.generatedURL = new URL(this.getURL());
			
			if (this.hasRawData) {
				if (this.parameters.size() > 1) {
					HttpMultipartGenerator multipartGenerator = new HttpMultipartGenerator("UTF-8");
					this.contentType = multipartGenerator.getContentType();
					
					try {
						for (Parameter parameter : this.parameters) {
							if (parameter.isRawData()) {
								if (parameter.getValue() instanceof InputStream) {
									multipartGenerator.appendParameter(parameter.getName(), (InputStream)parameter.getValue(), parameter.getFileName());
								} else if (parameter.getValue() instanceof byte[]) {
									multipartGenerator.appendParameter(parameter.getName(), (byte[])parameter.getValue(), parameter.getFileName());
								} else {
									throw new RuntimeException("Unknown raw data type");
								}
							} else {
								String value = parameter.getValue().toString();
								multipartGenerator.appendParameter(parameter.getName(), value);
							}
						}
						this.body = multipartGenerator.generateBody();
						this.contentLength  = multipartGenerator.getContentLength();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					this.contentType = "application/octet-stream";
					Object value = this.parameters.get(0).getValue();
					
					if (value instanceof byte[]) {
						this.body = new ByteArrayInputStream((byte[])value);
					} else {
						this.body = (InputStream)value;
					}
				}
			} else {
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
			}
			
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
