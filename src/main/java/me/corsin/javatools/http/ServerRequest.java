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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.corsin.javatools.http.APICommunicator.IResponseTransformer;
import me.corsin.javatools.properties.InvalidConfigurationException;
import me.corsin.javatools.properties.SharedProperties;
import me.corsin.javatools.task.Task;
import me.corsin.javatools.task.TaskQueue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ServerRequest {

	////////////////////////
	// NESTED CLASSES
	////////////////

	public interface ITypedResponseHandler<T> {
		void onResponseReceived(ServerRequest request, T response, Throwable thrownException);
	}

	public interface IResponseHandler extends ITypedResponseHandler<Object> { }

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
	private TaskQueue taskQueue;
	private boolean hasRawData;
	private boolean forceMultipart;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ServerRequest() {
		this(null, null, null);
	}

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
		if (requestType == null) {
			requestType = HttpMethod.GET;
		}

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
		StringBuilder finalUrl = new StringBuilder();
		finalUrl.append(this.url);

		if (!this.parameters.isEmpty()) {
			finalUrl.append('?');

			boolean first = true;
			for (Parameter parameter : this.parameters) {
				if (!first) {
					finalUrl.append('&');
				}
				try {
					finalUrl.append(parameter.getName() + "="  + URLEncoder.encode(parameter.getValue().toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				first = false;
			}
		}

		return finalUrl.toString();
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

	public ServerRequest addParameter(String name, Object[] params) {
		for (Object param : params) {
			if (param instanceof DateTime) {
				this.addParameter(name, (DateTime)param);
			} else {
				this.addParameter(name, param);
			}
		}
		return this;
	}

	/**
	 * Add a generic parameter. ToString is called on the value
	 * @param name
	 * @param value
	 * @return
	 */
	public ServerRequest addParameter(String name, Object value) {
		return this.addParameter(name, value.toString());
	}

	public ServerRequest addParameter(String name, DateTime date) {
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

		this.addParameter(name, fmt.print(date));

		return this;
	}

	public ServerRequest addParameter(String name, Number number) {
		return this.addParameter(new Parameter(name, number.toString()));
	}

	public ServerRequest addParameter(String name, String value) {
		return this.addParameter(new Parameter(name, value));
	}

	/**
	 * Add a generic parameter.
	 * If the value is an InputStream or a Byte[], it will be send as raw data to the server
	 * If the value is something else, toString will be called on the object instance to get
	 * a string representation to send to the server
	 * @param name
	 * @param value
	 * @param fileName
	 * @return
	 */
	public ServerRequest addParameter(String name, Object value, String fileName) {
		return this.addParameter(new Parameter(name, value, fileName));
	}

	public ServerRequest removeParameter(String name) {
		Iterator<Parameter> it = this.parameters.iterator();

		while (it.hasNext()) {
			Parameter param = it.next();

			if (param.getName().equals(name)) {
				it.remove();
			}
		}

		return this;
	}

	public ServerRequest addParameter(Parameter parameter) {
		this.parameters.add(parameter);
		if (parameter.isRawData()) {
			this.hasRawData = true;
		}

		return this;
	}

	public void generate() throws MalformedURLException {
		switch (this.getHttpMethod()) {
		case POST:
		case PUT:
			this.generatedURL = new URL(this.getURL());

			if (this.hasRawData || this.forceMultipart) {
				if (this.parameters.size() > 1 || this.forceMultipart) {
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

		if (this.contentType != null) {
			this.getHeaders().put("Content-Type", this.contentType);
			this.getHeaders().put("Content-Length", Long.toString(this.contentLength));
		}
	}

	public Task<Object> getResponseAsync() {
		Task<Object> task = new Task<Object>(this) {

			@Override
			protected Object perform() throws Throwable {
				return ServerRequest.this.getResponse();
			}
		};

		TaskQueue taskQueue = this.getTaskQueue();
		if (taskQueue != null) {
			taskQueue.executeAsync(task);
		} else {
			new Thread(task).start();
		}

		return task;
	}

	@SuppressWarnings("unchecked")
	public <T> Task<T> getResponseAsync(final Class<T> responseType) {
		this.setExpectedResponseType(responseType);

		return (Task<T>)this.getResponseAsync();
	}

	public Object getResponse() throws IOException {
		return this.getResponse(this.expectedResponseType);
	}

	@SuppressWarnings("unchecked")
	public <T> T getResponse(Class<T> responseNodeType) throws IOException {
		this.setExpectedResponseType(responseNodeType);

		APICommunicator communicator = new APICommunicator();

		communicator.setResponseTransformer(this.getResponseTransformer());

		return (T)communicator.getResponse(this);
	}

	protected void configureResponseTransformerUsingSharedProperties(String sharedPropertyName, String sharedPropertyNameClass) {
		Object responseTransformer = SharedProperties.getSharedInstance().get(sharedPropertyName);

		if (responseTransformer == null) {
			String jsonResponseTransformer = (String)SharedProperties.getSharedInstance().get(sharedPropertyNameClass);

			if (jsonResponseTransformer != null) {
				try {
					responseTransformer = Class.forName(jsonResponseTransformer).newInstance();
				} catch (Exception e) {
					throw new InvalidConfigurationException("Unable to instantiate the response transformer class " + jsonResponseTransformer);
				}
			}
		}

		if (responseTransformer == null) {
//			throw new InvalidConfigurationException("You need to have the \"" + sharedPropertyName + "\" or the " +
//					"\"" + sharedPropertyName + "Class\" property set in the SharedProperties instance");
		}

		try {
			this.setResponseTransformer((IResponseTransformer)responseTransformer);
		} catch (ClassCastException e) {
			throw new InvalidConfigurationException("The set response transformer does not implement IResponseTransformer");
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public final String getURL() {
		return this.url;
	}

	public ServerRequest setURL(String url) {
		this.url = url;

		return this;
	}

	public URL getGeneratedURL() {
		return this.generatedURL;
	}

	public final HttpMethod getHttpMethod() {
		return this.httpMethod;
	}

	public final ServerRequest setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;

		return this;
	}

	public Class<?> getExpectedResponseType() {
		return this.expectedResponseType;
	}

	public ServerRequest setExpectedResponseType(Class<?> responseType) {
		this.expectedResponseType = responseType;

		return this;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public List<Parameter> getParameters() {
		return this.parameters;
	}

	public InputStream getBody() {
		return this.body;
	}

	public String getContentType() {
		return this.contentType;
	}

	public long getContentLength() {
		return this.contentLength;
	}

	public IResponseTransformer getResponseTransformer() {
		return this.responseTransformer;
	}

	public ServerRequest setResponseTransformer(IResponseTransformer responseTransformer) {
		this.responseTransformer = responseTransformer;

		return this;
	}

	public TaskQueue getTaskQueue() {
		return this.taskQueue;
	}

	public void setTaskQueue(TaskQueue taskQueue) {
		this.taskQueue = taskQueue;
	}

	public boolean isForceMultipart() {
		return this.forceMultipart;
	}

	public void setForceMultipart(boolean forceMultipart) {
		this.forceMultipart = forceMultipart;
	}
}
