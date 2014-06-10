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
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (!(obj instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) obj;
        if (this.first == null) {
            if (pair.getFirst() != null) return false;
        } else {
            if (!first.equals(pair.getFirst())) return false;
        }

        if (this.second == null) {
            if (pair.getSecond() != null) return false;
        } else {
            if (!second.equals(pair.getSecond())) return false;
        }

        return true;
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
