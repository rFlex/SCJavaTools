package me.corsin.javatools.reflect;
/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.wsframework.utils
// ReflectionUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 20, 2013 at 1:44:09 PM
////////



import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

	////////////////////////
	// VARIABLES
	////////////////

	////////////////////////
	// CONSTRUCTORS
	////////////////

	////////////////////////
	// METHODS
	////////////////

	public static boolean setField(Object object, Field field, Object value) {
		return setField(object, field.getName(), value);
	}
	
	/*
	 * Set a field using reflection
	 */
	public static boolean setField(Object object, String fieldName, Object value) {
		char c = fieldName.charAt(0);
		
		if (c >= 'a' && c <= 'z') {
			c -= 'a' - 'A';
			fieldName = c + fieldName.substring(1);
		}
		
		String methodName = "set" + fieldName;
		Class<?>[] returnType = new Class<?>[1];
		Class<?> objectClass = object.getClass();
		
		Method getMethod = getMethod(objectClass, "get" + fieldName);
		
		if (getMethod == null) {
			getMethod = getMethod(objectClass, "is" + fieldName);
		}
		
		if (getMethod == null) {
			return false;
		}
		
		returnType[0] = getMethod.getReturnType();
		
		return silentInvoke(object, methodName, returnType, value);
	}
	
	public static Method getMethod(Class<?> aClass, String methodName, Class<?> ... args) {
		try {
			return aClass.getMethod(methodName, args);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Silently invoke the specified methodName using reflection.
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return true if the invoke was successful
	 */
	public static boolean silentInvoke(Object object, String methodName, Object ... parameters) {
		Class<?>[] parametersTypes = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parametersTypes[i] = parameters[i].getClass();
		}
		
		return silentInvoke(object, methodName, parametersTypes, parameters);
	}
	
	public static boolean silentInvoke(Object object, String methodName, Class<?>[] parameterTypes, Object ... parameters) {
		try {
			Method method = object.getClass().getMethod(methodName, parameterTypes);
			method.invoke(object, parameters);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
