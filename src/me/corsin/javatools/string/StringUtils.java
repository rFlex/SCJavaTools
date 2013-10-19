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
	
    private static final String allowedChars = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN0123456789";
    public static String randomAlphaNumericString(int size) {
        char[] randomStr = new char[size];

        Random random = new Random(new Date().getTime());

        for (int i = 0; i < size; i++) {
            randomStr[i] = allowedChars.charAt(random.nextInt(allowedChars.length()));
        }

        return new String(randomStr);
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
	
	public static String getObjectDescription(Object object) {
		StringBuilder sb = new StringBuilder();
		
		if (object == null) {
			sb.append("null");
		} else if (object instanceof String) {
			sb.append((String)object);
		} else if (object instanceof Object[]) {
			Object[] array = (Object[])object;
			sb.append("[");
			
			boolean isFirst = true;
			for (Object obj : array) {
				if (!isFirst) {
					sb.append(", ");
				}
				sb.append(getObjectDescription(obj));
				isFirst = false;
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

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
