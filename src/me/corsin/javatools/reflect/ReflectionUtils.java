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
	
	public static Object getField(Object object, String fieldName) {
		String propertyName = fieldName;
		char c = fieldName.charAt(0);
		
		if (c >= 'a' && c <= 'z') {
			c -= 'a' - 'A';
			fieldName = c + fieldName.substring(1);
		}
		
		Class<?> objectClass = object.getClass();
		
		Method getMethod = getMethod(objectClass, "get" + fieldName);
		
		if (getMethod == null) {
			getMethod = getMethod(objectClass, "is" + fieldName);
		}
		
		if (getMethod == null) {
			getMethod = getMethod(objectClass, propertyName);
		}
		
		if (getMethod == null) {
			throw new RuntimeException("No getter found for property " + propertyName + " on class " + objectClass.getSimpleName());
		}
		
		return invoke(object, getMethod);
	}
	
	public static Method getMethod(Class<?> aClass, String methodName, Class<?> ... args) {
		try {
			return aClass.getMethod(methodName, args);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Method getMethod(Class<?> cls, String methodName, Object ... parameters) {
		Method method = null;
		
		for (Method classMethod : cls.getMethods()) {
			if (classMethod.getName().equals(methodName)) {
				Class<?>[] parametersType = classMethod.getParameterTypes();
				boolean match = false;
				
				if (parametersType.length == parameters.length) {
					match = true;
					for (int i = 0, length = parametersType.length; i < length; i++) {
						if (parameters[i] != null) {
							if (!parametersType[i].isAssignableFrom(parameters[i].getClass())) {
								System.out.println("Not assignable: " + parametersType[i].getSimpleName());
								System.out.println(parameters[i].getClass().getSimpleName());
								match = false;
								break;
							}
						}
					}
				}
				
				if (match) {
					method = classMethod;
				}
				break;
			}
		}
		return method;
	}
	
	/**
	 * Silently invoke the specified methodName using reflection.
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return true if the invoke was successful
	 */
	public static Object invoke(Object object, String methodName, Object ... parameters) {
		Method method = getMethod(object.getClass(), methodName, parameters);
		
		if (method != null) {
			return invoke(object, method, parameters);
		} else {
			throw new RuntimeException("No method that matches [" + methodName + "] for class " + object.getClass().getSimpleName());
		}
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
	
	public static Object invoke(Object object, Method method, Object ... parameters) {
		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			return null;
		}
	}
	
	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
