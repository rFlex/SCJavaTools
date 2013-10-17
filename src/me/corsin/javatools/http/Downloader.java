/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.http
// Downloader.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 14, 2013 at 12:22:07 PM
////////

package me.corsin.javatools.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import me.corsin.javatools.io.IOUtils;

public class Downloader {

	////////////////////////
	// VARIABLES
	////////////////
	
	public static final String DEFAULT_CHARSET = "UTF-8";

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////

	public static String downloadAsString(URL url) throws IOException {
		return downloadAsString(url, DEFAULT_CHARSET);
	}
	
	public static String downloadAsString(URL url, String charset) throws IOException {
		return new String(downloadAsByteArray(url), charset);
	}
	
	public static String downloadAsString(String urlStr) throws IOException {
		return downloadAsString(urlStr, DEFAULT_CHARSET);
	}
	
	public static String downloadAsString(String urlStr, String charset) throws IOException {
		return new String(downloadAsByteArray(urlStr), charset);
	}
	
	public static byte[] downloadAsByteArray(String urlStr) throws IOException {
		return downloadAsByteArray(new URL(urlStr));
	}
	
	public static byte[] downloadAsByteArray(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		InputStream stream = connection.getInputStream();
		
		try {
			return IOUtils.readStream(stream);
		} finally {
			stream.close();
		}
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
