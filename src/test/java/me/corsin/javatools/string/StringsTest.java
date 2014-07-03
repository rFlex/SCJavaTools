package me.corsin.javatools.string;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class StringsTest {
	@Test
	public void format_with_a_variable_replaces_the_variable_in_the_template() {
		// GIVEN
		String tpl = "Hello {#0}!";
		String var = "World";

		// WHEN
		String result = Strings.format(tpl, var);

		// THEN
		assertEquals("Hello World!", result);
	}
}
