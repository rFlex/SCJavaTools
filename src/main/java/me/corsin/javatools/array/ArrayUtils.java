/////////////////////////////////////////////////
// Project : webservice
// Package : com.ever.webservice.api.utils
// Utils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 16, 2013 at 12:52:08 PM
////////

package me.corsin.javatools.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////

	public static <T> boolean arrayContains(T[] array, T object) {
		for (T arrayObject : array) {
			if (arrayObject.equals(object)) {
				return true;
			}
		}

		return false;
	}

	public static <T> List<T> createReversed(List<T> input) {
		List<T> reversedList = new ArrayList<T>(input.size());

		for (int i = input.size() - 1; i >= 0; i--) {
			reversedList.add(input.get(i));
		}

		return reversedList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] addItem(T[] array, T object) {
		final T[] newArray = (T[])Array.newInstance(object.getClass(), array.length + 1);

		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = object;

		return newArray;
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
