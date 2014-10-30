/////////////////////////////////////////////////
// Project : webservice
// Package : com.ever.webservice.api.utils
// Utils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jan 16, 2013 at 12:52:08 PM
////////

package me.corsin.javatools.array;

import me.corsin.javatools.misc.NullArgumentException;

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
		if (array == null) {
			throw new NullArgumentException("array");
		}

		for (T arrayObject : array) {
			if (arrayObject.equals(object)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return Whether the array contains any of the object in the second given array
	 */
	public static <T> boolean containsAny(T[] array, T[] objects) {
		if (array == null) {
			throw new NullArgumentException("array");
		}

		if (objects == null) {
			throw new NullArgumentException("objects");
		}

		for (T item : array) {

			for (T obj : objects) {
				if (item.equals(obj)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @return Whether the array contains all objects in the second given array
	 */
	public static <T> boolean containsAll(T[] array, T[] objects) {
		if (array == null) {
			throw new NullArgumentException("array");
		}

		if (objects == null) {
			throw new NullArgumentException("objects");
		}

		for (T obj : array) {
			boolean hasObj = false;

			for (int i = 0; !hasObj && i < array.length; i++) {
				if (array[i].equals(obj)) {
					hasObj = true;
				}
			}

			if (!hasObj) {
				return false;
			}
		}

		return true;
	}

	public static <T> List<T> createReversed(List<T> input) {
		if (input == null) {
			throw new NullArgumentException("input");
		}

		List<T> reversedList = new ArrayList<T>(input.size());

		for (int i = input.size() - 1; i >= 0; i--) {
			reversedList.add(input.get(i));
		}

		return reversedList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] addItem(T[] array, T object) {
		if (array == null) {
			throw new NullArgumentException("array");
		}

		final T[] newArray = (T[])Array.newInstance(object.getClass(), array.length + 1);

		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = object;

		return newArray;
	}


	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
