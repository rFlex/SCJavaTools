/////////////////////////////////////////////////
// Project : Mindie WebService
// Package : com.ever.wsframework.utils
// StringOutputStream.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 11, 2013 at 12:29:34 PM
////////

package me.corsin.javatools.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

public class HttpMultipartGenerator {

	////////////////////////
	// VARIABLES
	////////////////

	final private String encoding;
	final private Charset charset;
	final private String endLine;
	final private String boundary;
	final private ByteArrayOutputStream outputStream;
	final private String contentType;
	private boolean finished;
	
	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public HttpMultipartGenerator() {
		this("UTF-8");
	}
	
	public HttpMultipartGenerator(String encoding) {
		this.endLine = "\r\n";
		this.encoding = encoding;
		this.boundary = "------------------" + new Date().getTime();
		this.charset = Charset.forName(encoding);
		this.contentType = "multipart/form-data; boundary=" + boundary;
		this.outputStream = new ByteArrayOutputStream();
	}

	////////////////////////
	// METHODS
	////////////////

	private void write(String str) {
		this.write(str.getBytes(this.charset));
	}
	
	private void write(byte[] data) {
		this.write(data, data.length);
	}
	
	private void write(byte[] data, int length) {
		this.outputStream.write(data, 0, length);
	}
	
	private void writeBoundary() {
		this.write("--" + this.boundary);
	}
	
	private void writeEndline() {
		this.write(this.endLine);
	}
	
	private void writeFile(String name, String fileName) {
		this.writeBoundary();
		this.writeEndline();
		this.write("Content-Disposition: form-data; name=\"" + name + "\"");
		
		if (fileName != null) {
			this.write("; filename=\"" + fileName + "\"");
		}
		
		this.writeEndline();
		this.write("Content-Type: application/octet-stream");
		this.writeEndline();
		this.writeEndline();
	}
	
	public void appendParameter(String name, byte[] data, String fileName) {
		this.writeFile(name, fileName);
		this.write(data);
		this.writeEndline();
	}
	
	public void appendParameter(String name, InputStream inputStream, String fileName) throws IOException {
		this.writeFile(name, fileName);
		
		byte[] buffer = new byte[8192];
		
		int read;
		while ((read = inputStream.read(buffer)) > 0) {
			this.write(buffer, read);
		}
		
		this.writeEndline();
	}
	
	public void appendParameter(String name, String value) {
		this.writeBoundary();
		this.writeEndline();
		this.write("Content-Disposition: form-data; name=\"" + name + "\"");
		this.writeEndline();
		this.writeEndline();
		this.write(value);
		this.writeEndline();
	}
	
	private void finish() {
		if (this.finished) {
			throw new RuntimeException("The Multipart was already finished");
		}
		
		this.writeBoundary();
		this.write("--");
		this.writeEndline();
		this.finished = true;
	}
	
	public InputStream generateBody() {
		if (!this.finished) {
			this.finish();
		}
		return new ByteArrayInputStream(this.outputStream.toByteArray());
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
	
	public String getEncoding() {
		return encoding;
	}

	public String getEndLine() {
		return endLine;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	public long getContentLength() {
		return this.outputStream.size();
	}

	public boolean isFinished() {
		return finished;
	}
	
	public String getContentType() {
		return this.contentType;
	}
}
