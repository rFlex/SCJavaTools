/////////////////////////////////////////////////
// Project : JEmoji
// Package : 
// JEmojis.java
//
// Author : Simon CORSIN <simoncorsin@gmail.com>
// File created on Mar 19, 2014 at 2:56:44 PM
////////

package me.corsin.javatools.string;

/**
 * All the information on the emojis are available on http://www.iemoji.com
 */
public enum JEmoji {
    HEART(":heart:", 0xE2, 0x9D, 0xA4),
    V(":v:", 0xE2, 0x9C, 0x8C),
    STAR(":star:", 0xE2, 0xAD, 0x90),
    EMAIL(":email:", 0xE2, 0x9C, 0x89),
    EYES(":eyes:", 0xF0, 0x9F, 0x91, 0x80),
    WINK(":wink:", 0xF0, 0x9F, 0x98, 0x89);

    private final String shortCode;
    private final String utf8HexStr;

    private JEmoji(String shortCode, int... bytes) {
        this.shortCode = shortCode;
        this.utf8HexStr = fromValues(bytes);
    }

    private static String fromValues(int... intValues) {
        byte[] values = new byte[intValues.length];
        for (int i = 0, length = values.length; i < length; i++) {
            values[i] = (byte) intValues[i];
        }
        return new String(values);
    }

    public String getUTF8HexString() {
        return this.utf8HexStr;
    }

    public String getShortCode() {
        return this.shortCode;
    }

    public static String toUTF8HexString(String str) {
        String tmp = str;
        for (JEmoji emoji : values())
            tmp = tmp.replace(emoji.shortCode, emoji.utf8HexStr);
        return tmp;
    }
}
