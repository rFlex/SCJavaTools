/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.test
// Test.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 8:18:08 PM
////////

package me.corsin.javatools.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.corsin.javatools.misc.Pair;
import me.corsin.javatools.reflect.ReflectionUtils;

public class TestRunner {

	////////////////////////
	// VARIABLES
	////////////////
	
	private List<Class<?>> classToTests;
	private boolean showException;

	////////////////////////
	// CONSTRUCTORS
	////////////////
	
	public TestRunner() {
		this.classToTests = new ArrayList<Class<?>>();
	}

	////////////////////////
	// METHODS
	////////////////

	public Throwable runTest(Method method, Object obj) {
		try {
			method.invoke(obj);
			return null;
		} catch (Throwable e) {
			if (e.getCause() != null) {
				return e.getCause();
			}
			return e;
		}
	}
	
	public Throwable[] runTest(Object instanceToTry) {
		List<Throwable> thrownExceptions = new ArrayList<Throwable>();
		Pair<Method, TestCase>[] methods = ReflectionUtils.getMethodsWithAnnotation(instanceToTry.getClass(), TestCase.class);
		
		for (Pair<Method, TestCase> method : methods) {
			System.out.print("\tTesting " + method.getFirst().getName() + ": ");
			System.out.flush();
			
			Throwable e = this.runTest(method.getFirst(), instanceToTry);
			
			if (e != null) {
				thrownExceptions.add(e);
				System.out.println("NOK (" + e.getMessage() + ")");
				
				if (this.isShowException()) {
					e.printStackTrace();
				}
			} else {
				System.out.println("OK");
			}
		}
		return thrownExceptions.toArray(new Throwable[thrownExceptions.size()]);
	}
	
	public Throwable[] runTest(Class<?> cls) throws InstantiationException, IllegalAccessException {
		Object objectInstance = cls.newInstance();
		
		return this.runTest(objectInstance);
	}
	
	public void runTests() {
		for (Class<?> cls : this.classToTests) {
			System.out.println("--- Running test Class " + cls.getSimpleName() + " ---");
			
			try {
				this.runTest(cls);
			} catch (Exception e) {
				System.out.println("Failed ton instantiate class: " + e.getMessage());
				e.printStackTrace();
			}
			
			System.out.println("--- End test Class " + cls.getSimpleName() +  " ---");
		}
	}
	
	public void addTestClass(Class<?> cls) {
		this.classToTests.add(cls);
	}
	
	public static void runSingleTest(Class<?> forClass) {
		TestRunner testRunner = new TestRunner();
		testRunner.addTestClass(forClass);
		
		testRunner.runTests();
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public boolean isShowException() {
		return showException;
	}

	public void setShowException(boolean showException) {
		this.showException = showException;
	}
}
