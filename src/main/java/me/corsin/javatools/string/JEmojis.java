/////////////////////////////////////////////////
// Project : JEmoji
// Package : 
// JEmojis.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Mar 19, 2014 at 2:56:44 PM
////////

package me.corsin.javatools.string;

public class JEmojis {

    ////////////////////////
    // VARIABLES
    ////////////////

    ////////////////////////
    // CONSTRUCTORS
    ////////////////

    private JEmojis() {

    }

    ////////////////////////
    // METHODS
    ////////////////

    public static String fromValues(int... intValues) {
        byte[] values = new byte[intValues.length];
        for (int i = 0, length = values.length; i < length; i++) {
            values[i] = (byte) intValues[i];
        }

        return new String(values);
    }

    //
    public static void main(String[] args) {
        System.out.println(JEmojis.heart());
        System.out.println(JEmojis.eyes());
        System.out.println(JEmojis.envelope());
        System.out.println(JEmojis.victoryHand());
        System.out.println(JEmojis.whiteMediumStar());
        System.out.println(JEmojis.wink());
    }

    ////////////////////////
    // EMOJIS
    ////////////////

    public static String heart() {
        return fromValues(0xE2, 0x9D, 0xA4);
    }

    public static String victoryHand() {
        return fromValues(0xE2, 0x9C, 0x8C);
    }

    public static String whiteMediumStar() {
        return fromValues(0xE2, 0xAD, 0x90);
    }

    public static String envelope() {
        return fromValues(0xE2, 0x9C, 0x89);
    }

    public static String eyes() {
        return fromValues(0xF0, 0x9F, 0x91, 0x80);
    }

    public static String wink() {
        return fromValues(0xF0, 0x9F, 0x98, 0x89);
    }

    ////////////////////////
    // SINGLETON
    ////////////////

//	private static JEmojis sharedInstance;
//	public static JEmojis getSharedInstance() {
//		if (sharedInstance == null) {
//			synchronized (JEmojis.class) {
//				if (sharedInstance == null) {
//					sharedInstance = new JEmojis();
//				}
//			}
//		}
//		
//		return sharedInstance;
//	}
}
