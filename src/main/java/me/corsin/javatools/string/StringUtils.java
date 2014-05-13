/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.wsframework.utils
// StringUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 2, 2013 at 1:40:18 PM
////////

package me.corsin.javatools.string;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Date;
import java.util.Random;

public class StringUtils {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////
	
	public static String randomStringInRange(String allowedChars, int size) {
		char[] randomStr = new char[size];
		
		Random random = new Random(new Date().getTime());
		
		final int allowedCharsLength = allowedChars.length();
		for (int i = 0; i < size; i++) {
			randomStr[i] = allowedChars.charAt(random.nextInt(allowedCharsLength));
		}
		
		return new String(randomStr);
	}
	
    private static final String allowedChars = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN0123456789";
    public static String randomAlphaNumericString(int size) {
    	return randomStringInRange(allowedChars, size);
    }
    
    private static final String allowedNumericChars = "0123456789";
    public static String randomNumericString(int size) {
    	return randomStringInRange(allowedNumericChars, size);
    }
    
	public static String randomString(int minSize, int maxSize) {
		final Random random = new Random();
		
		int stringSize = random.nextInt(maxSize - minSize) + minSize;
		
		final StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < stringSize; i++) {
			char nextChar = (char)(random.nextInt((int)'~' - (int)'!') + ('!'));
			sb.append(nextChar);
		}
		
		return sb.toString();
	}
	
	public static String firstCharUpperCase(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
	public static void printObjectDescription(Object object) {
		System.out.println(getObjectDescription(object));
	}
	
	@SuppressWarnings("rawtypes")
	public static String getObjectDescription(Object object) {
		StringBuilder sb = new StringBuilder();
		
		if (object == null) {
			sb.append("null");
		} else if (object instanceof String) {
			sb.append((String)object);
		} else if (object instanceof Iterable || object instanceof Object[]) {
			sb.append("[");
			
			boolean isFirst = true;
			if (object instanceof Iterable) {
				for (Object obj : (Iterable)object) {
					if (!isFirst) {
						sb.append(", ");
					}
					sb.append(getObjectDescription(obj));
					isFirst = false;
				}
			} else {
				for (Object obj : (Object[])object) {
					if (!isFirst) {
						sb.append(", ");
					}
					sb.append(getObjectDescription(obj));
					isFirst = false;
				}
			}
		
			
			sb.append("]");
		} else {
			sb.append(object.toString());
		}
		
		return sb.toString();
	}
	
	public static boolean contains(String str, char c) {
		for (int i = 0, length = str.length(); i < length; i++) {
			if (c == str.charAt(i)) {
				return true;
			}
		}
		return false;
	}
	
	public static String replacePathExtension(String fileName, String newPathExtension) {
		String output = fileName;

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    output = fileName.substring(0, i + 1) + newPathExtension;
		}
		
		return output;
	}
	
	public static String getPathExtension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		
		return extension;
	}
	
	public static String cleanString(String str) {
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder utf8Decoder = charset.newDecoder();
		utf8Decoder.onMalformedInput(CodingErrorAction.IGNORE);
		utf8Decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
		ByteBuffer bytes = ByteBuffer.wrap(str.getBytes(charset));
		CharBuffer parsed;
		
		try {
			parsed = utf8Decoder.decode(bytes);
			return parsed.toString();
		} catch (CharacterCodingException e) {
			return "";
		}
		
	}
	
	public static boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		}
		return str.trim().length() == 0;
	}
	
	public static boolean equals(String str, String str2) {
		if (str == null && str2 == null) {
			return true;
		}
		if (str == null) {
			return false;
		}
		if (str2 == null) {
			return false;
		}
		
		return str.equals(str2);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
