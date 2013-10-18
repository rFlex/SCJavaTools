/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Randomizer.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 18, 2013 at 6:45:20 PM
////////

package me.corsin.javatools.misc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import me.corsin.javatools.string.StringUtils;

public class Randomizer {

	////////////////////////
	// VARIABLES
	////////////////
	
	private static Random random = new Random(new Date().getTime());

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////

	public static int nextInt() {
		return random.nextInt();
	}
	
	public static int nextInt(int max) {
		return random.nextInt(max);
	}
	
	public static int nextInt(int min, int max) {
		return nextInt(max - min) + min;
	}
	
	public static float nextFloat() {
		return random.nextFloat();
	}
	
	public static double nextDouble() {
		return random.nextDouble();
	}
	
	public static String randomString(int minSize, int maxSize) {
		return StringUtils.randomString(minSize, maxSize);
	}
	
	public static String randomAlphaNumeric(int size) {
		return StringUtils.randomAlphaNumericString(size);
	}
	
	public static <T> List<T> createRandomizedList(List<T> list) {
		List<T> randomized = new ArrayList<T>();
		List<T> remaining = new ArrayList<T>(list);
		
		while (!remaining.isEmpty()) {
			int position = nextInt(remaining.size());
			randomized.add(remaining.get(position));
			remaining.remove(position);
		}
		
		return randomized;
	}
	
	public static <T> T randomElement(List<T> elements) {
		return elements.get(random.nextInt(elements.size()));
	}
	
	public static <T> T randomElement(T[] array) {
		return array[random.nextInt(array.length)];
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
