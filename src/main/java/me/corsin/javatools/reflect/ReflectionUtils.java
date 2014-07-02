package me.corsin.javatools.reflect;
/////////////////////////////////////////////////
// Project : Ever WebService
// Package : com.ever.wsframework.utils
// ReflectionUtils.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Aug 20, 2013 at 1:44:09 PM
////////

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.misc.Pair;

import org.apache.commons.lang3.ClassUtils;

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

	public static Object newInstance(String className, Object ... parameters) {
		try {
			Class<?> objClass = Class.forName(className);
			Constructor<?> constructor = getConstructor(objClass, parameters);

			if (constructor != null) {
				return constructor.newInstance(parameters);
			}
		} catch (Exception e) {
		}

		return null;
	}

	public static boolean setField(Object object, Field field, Object value) {
		return setField(object, field.getName(), value);
	}

	public static boolean setPublicField(Object object, String fieldName, Object value) {
		Class<?> objectClass = object.getClass();
		Field field;
		try {
			field = objectClass.getField(fieldName);
			field.set(object, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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

	public static Constructor<?> getConstructor(Class<?> cls, Object ... parameters) {
		Constructor<?> constructor = null;

		for (Constructor<?> classMethod : cls.getConstructors()) {
			Class<?>[] parametersType = classMethod.getParameterTypes();
			boolean match = false;

			if (parametersType.length == parameters.length) {
				match = true;
				for (int i = 0, length = parametersType.length; i < length; i++) {
					if (parameters[i] != null) {
						if (!parametersType[i].isAssignableFrom(parameters[i].getClass())) {
							match = false;
							break;
						}
					}
				}
			}

			if (match) {
				constructor = classMethod;
				break;
			}
		}
		return constructor;
	}

	public static Method getMethodThatMatchesParameters(Class<?> cls, String methodName, Object ... parameters) {
		Method method = null;

		for (Method classMethod : cls.getMethods()) {
			if (classMethod.getName().equals(methodName)) {
				Class<?>[] parametersType = classMethod.getParameterTypes();
				boolean match = false;

				if (parametersType.length == parameters.length) {
					match = true;
					for (int i = 0, length = parametersType.length; i < length; i++) {
						if (parameters[i] != null) {
							Class<?> parameterClass = parameters[i].getClass();
							Class<?> methodParameterClass = parametersType[i];

							if (!ClassUtils.isAssignable(parameterClass, methodParameterClass, true)) {
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
		Method method = getMethodThatMatchesParameters(object.getClass(), methodName, parameters);

		if (method != null) {
			try {
				return method.invoke(object, parameters);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unable to invoke method", e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to invoke method", e);
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof RuntimeException) {
					throw (RuntimeException)e.getCause();
				}

				throw new RuntimeException(e.getCause());
			}
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

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> Pair<Method, T>[] getMethodsWithAnnotation(Class<?> cls, Class<T> annotationCls) {
		List<Pair<Method, T>> methods = new ArrayList<Pair<Method, T>>();

		for (Method method : cls.getMethods()) {
			T annotation = method.getAnnotation(annotationCls);
			if (annotation != null) {
				methods.add(new Pair<Method, T>(method, annotation));
			}
		}

		return methods.toArray(new Pair[methods.size()]);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////
}
