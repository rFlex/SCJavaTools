/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Maps.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jul 30, 2014 at 7:26:37 PM
////////

package me.corsin.javatools.misc;

import java.util.List;
import java.util.Map;

public class Maps {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public Maps() {

	}

	////////////////////////
	// METHODS
	////////////////

	public static Object getValue(String path, Object map) {
		return getValue(path, "\\.", map);
	}

	@SuppressWarnings("rawtypes")
	public static Object getValue(String path, String separator, Object map) {
		String[] paths = path.split(separator);

		Object output = map;
		String lastKey = "root";

		for (int i = 0; output != null && i < paths.length; i++) {
			String key = paths[i];

			if (output instanceof Map) {
				String effectiveKey = key;
				Integer requestedIndex = null;

				if (effectiveKey.endsWith("]")) {
					int bracketIndex = effectiveKey.indexOf('[');
					effectiveKey = effectiveKey.substring(0, bracketIndex);
					String number = key.substring(bracketIndex + 1, key.length() - 1);

					requestedIndex = Integer.valueOf(number);
				}

				Map mapOutput = (Map)output;
				output = mapOutput.get(effectiveKey);

				if (output != null && requestedIndex != null) {
					if (output instanceof List) {
						List list = (List)output;
						if (requestedIndex < list.size()) {
							output = list.get(requestedIndex);
						} else {
							output = null;
						}
					} else {
						throw new RuntimeException("Object on key [" + key + "] is not an array: " + output.getClass());
					}
				}
			} else {
				throw new RuntimeException("Object on key [" + lastKey + "] is not a Map: " + output.getClass());
			}
			lastKey = key;
		}

		return output;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
