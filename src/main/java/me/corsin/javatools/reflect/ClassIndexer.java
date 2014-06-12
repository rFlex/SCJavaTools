/////////////////////////////////////////////////
// Project : WSFramework
// Package : co.mindie.wsframework
// ComponentContext.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Jun 10, 2014 at 6:05:55 PM
////////

package me.corsin.javatools.reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassIndexer<T> {

	////////////////////////
	// VARIABLES
	////////////////

	private Map<Class<?>, List<T>> elementsForClass;
	private Class<?> baseClass;

	////////////////////////
	// CONSTRUCTORS
	////////////////

	public ClassIndexer() {
		this(Object.class);
	}

	public ClassIndexer(Class<?> baseClass) {
		this.elementsForClass = new HashMap<Class<?>, List<T>>();
		this.baseClass = baseClass;
	}

	////////////////////////
	// METHODS
	////////////////

	private boolean addSingle(T element, Class<?> accessibleClass) {
		List<T> elements = this.elementsForClass.get(accessibleClass);

		if (elements == null) {
			elements = new ArrayList<T>();
			this.elementsForClass.put(accessibleClass, elements);
		}

		if (!elements.contains(element)) {
			elements.add(element);
			return true;
		}
		return false;
	}

	private void removeSingle(T element, Class<?> accessibleClass) {
		List<T> elements = this.elementsForClass.get(accessibleClass);

		if (elements != null) {
			elements.remove(element);

			if (elements.isEmpty()) {
				this.elementsForClass.remove(accessibleClass);
			}
		}
	}

	/**
	 * Associate the element with accessibleClass and any of the the superclass and interfaces
	 * of the accessibleClass until baseClass
	 * @param element
	 * @param accessibleClass
	 * @return
	 */
	public boolean add(T element, Class<?> accessibleClass) {
		boolean added = false;

		while (accessibleClass != null && accessibleClass != this.baseClass) {
			added |= this.addSingle(element, accessibleClass);

			for (Class<?> interf : accessibleClass.getInterfaces()) {
				this.addSingle(element, interf);
			}

			accessibleClass = accessibleClass.getSuperclass();
		}

		return added;
	}

	/**
	 * Remove the element from accessible class and any of the superclass and
	 * interfaces of the accessibleClass until baseClass
	 * @param element
	 * @param accessibleClass
	 */
	public void remove(T element, Class<?> accessibleClass) {
		while (accessibleClass != null && accessibleClass != this.baseClass) {
			this.removeSingle(element, accessibleClass);

			for (Class<?> interf : accessibleClass.getInterfaces()) {
				this.removeSingle(element, interf);
			}

			accessibleClass = accessibleClass.getSuperclass();
		}
	}

	/**
	 * Find the elements that are accessible with this accessibleClass
	 * @param accessibleClass
	 * @return
	 */
	public List<T> find(Class<?> accessibleClass) {
		return this.elementsForClass.get(accessibleClass);
	}

	////////////////////////
	// GETTERS/SETTERS
	////////////////

	public Class<?> getBaseClass() {
		return this.baseClass;
	}

	public void setBaseClass(Class<?> baseClass) {
		this.baseClass = baseClass;
	}
}
