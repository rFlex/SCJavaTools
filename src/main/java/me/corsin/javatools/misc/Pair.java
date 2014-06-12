/////////////////////////////////////////////////
// Project : SCJavaTools
// Package : me.corsin.javatools.misc
// Pair.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Oct 17, 2013 at 8:31:54 PM
////////

package me.corsin.javatools.misc;

public class Pair<T, T2> {

    ////////////////////////
    // VARIABLES
    ////////////////

    private T first;
    private T2 second;

    ////////////////////////
    // CONSTRUCTORS
    ////////////////

    public Pair(T first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {

    }

    ////////////////////////
    // METHODS
    ////////////////

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return !(first != null ? !first.equals(pair.first) : pair.first != null) && !(second != null ? !second.equals(pair.second) : pair.second != null);
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }


    ////////////////////////
    // GETTERS/SETTERS
    ////////////////

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T2 getSecond() {
        return second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }
}
