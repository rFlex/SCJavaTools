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

	public static String stackTraceToHTMLString(Throwable t) {
		return stackTraceToHTMLString(t, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;", "<br>");
	}

	public static String stackTraceToHTMLString(Throwable t, String tab, String newLine) {
		String text = stackTraceToString(t);
		if (text == null) {
			return null;
		} else {
			return text.replace("\n", newLine)
					.replace("\t", tab);
		}
	}
}
