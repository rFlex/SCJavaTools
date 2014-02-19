/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.test
// TestCaseFinalizer.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Dec 27, 2013 at 10:05:41 AM
////////

package me.corsin.javatools.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCaseFinalizer {

}
