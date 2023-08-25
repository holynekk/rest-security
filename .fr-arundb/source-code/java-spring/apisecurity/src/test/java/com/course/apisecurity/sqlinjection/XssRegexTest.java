package com.course.apisecurity.sqlinjection;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class XssRegexTest {

	@Test
	void testXssPattern() {
		var stringOriginal = "Anna <img src='x' onerror='alert(\"This is XSS\")'>";
		var regex = "onclick|onkeypress|onkeydown|onkeyup|onerror|onchange|onmouseover|onmouseout|onblur|onselect|onfocus";

		var stringWithoutLineBreak = stringOriginal.replaceAll("\\r\\n|\\r|\\n", "");

		var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		assertTrue(pattern.matcher(stringWithoutLineBreak).find());
	}

}
