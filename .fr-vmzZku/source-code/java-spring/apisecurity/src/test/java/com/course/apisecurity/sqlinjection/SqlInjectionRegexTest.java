package com.course.apisecurity.sqlinjection;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class SqlInjectionRegexTest {

	@Test
	void testDropPattern() {
		var stringOriginal = "	{\r\n" + "	    \"fullName\": \"Peter Parker\",\r\n"
				+ "	    \"email\": \"peter.parker@marvel.com\",\r\n" + "	    \"birthDate\": \"2000-12-31\",\r\n"
				+ "	    \"gender\": \"M', '2000-12-31'); DROP table jdbc_merchant --\"\r\n" + "	}\r\n" + "\r\n";
		var regex = "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)";

		var stringWithoutLineBreak = stringOriginal.replaceAll("\\r\\n|\\r|\\n", "");

		var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		assertTrue(pattern.matcher(stringWithoutLineBreak).matches());
	}

}
