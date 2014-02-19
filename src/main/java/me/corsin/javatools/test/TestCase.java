/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.test
// TestCase.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 8:23:31 PM
////////

package me.corsin.javatools.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCase {
	
	boolean stopIfFailed() default false;
	
}
