package me.corsin.javatools.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtils {
	public static String stackTraceToString(Throwable t) {
		if (t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			return sw.toString();
		} else {
			return null;
		}
	}
}
