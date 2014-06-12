package me.corsin.javatools.io;
/////////////////////////////////////////////////
// Project : GarbageProject
// Package : 
// IOUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Mar 1, 2013 at 5:49:36 PM
////////



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class IOUtils {

	// //////////////////////
	// VARIABLES
	// //////////////

	// //////////////////////
	// CONSTRUCTORS
	// //////////////

	// //////////////////////
	// METHODS
	// //////////////

	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				
			}
		}
	}
	
	public static String readStreamAsString(InputStream stream) throws IOException {
		return readStreamAsString(stream, "UTF-8");
	}
	
	public static String readStreamAsString(InputStream stream, String encoding) throws IOException {
		byte[] byteArray = readStream(stream);
		
		return new String(byteArray, encoding);
	}
	
	public static void saveArray(byte[] pictureBytes, OutputStream stream) {
		try {
			stream.write(pictureBytes);
		} catch (IOException e) {
			
		}
	}

	public static void saveArray(byte[] pictureBytes, File output) {
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(output);
			try {
				saveArray(pictureBytes, stream);
			} finally {
				closeStream((Closeable)stream);
			}

		} catch (FileNotFoundException e1) {

		}
	}

	public static byte[] readFile(String file) {
		return readFile(new File(file));
	}

	public static byte[] readFile(File file) {
		byte[] fileContent = null;

		try {
			RandomAccessFile f = null;
			try {
				f = new RandomAccessFile(file, "r");
				byte[] b = new byte[(int) f.length()];
				
				f.read(b);
				fileContent = b;
			} finally {
				f.close();
			}
		} catch (Exception e) {
			
		}

		return fileContent;
	}
	
	public static String readFileAsString(File file) throws IOException {
		return readStreamAsString(new FileInputStream(file));
	}
	
	public static byte[] readStream(InputStream stream) throws IOException {
		final ByteArrayOutputStream fs = new ByteArrayOutputStream();
		
		try {
			byte[] buffer = new byte[8192];
			
			int read = 0;
			while ((read = stream.read(buffer)) > 0) {
				fs.write(buffer, 0, read);
			}
		} finally {
			fs.close();
		}
		
		return fs.toByteArray();
	}
	
	public static void writeFile(String fileOutput, byte[] data) throws IOException {
		OutputStream outputStream = new FileOutputStream(fileOutput);
		try {
			outputStream.write(data);
		} finally {
			outputStream.close();
		}
	}
	
	public static int writeStream(OutputStream outputStream, InputStream inputStream, byte[] buffer) throws IOException {
		int totalWritten = 0;
		int read = 0;
		while ((read = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, read);
			totalWritten += read;
		}
		
		return totalWritten;
	}
	
	public static int writeStream(OutputStream outputStream, InputStream inputStream) throws IOException {
		return writeStream(outputStream, inputStream, new byte[8192]);
	}
	
	public static int writeStream(OutputStream stream, byte[] data) throws IOException {
		final InputStream fs = new ByteArrayInputStream(data);
		int totalWritten = writeStream(stream, fs);
		fs.close();
		
		return totalWritten;
	}

	public static String humanReadableByteCount(long bytes) {
		return humanReadableByteCount(bytes, true);
	}
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	// //////////////////////
	// GETTERS/SETTERS
	// //////////////
}
