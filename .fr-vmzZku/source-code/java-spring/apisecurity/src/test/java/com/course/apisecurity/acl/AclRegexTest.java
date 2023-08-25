package com.course.apisecurity.acl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AclRegexTest {

	private static final Logger LOG = LoggerFactory.getLogger(AclRegexTest.class);

	List<String> uris = List.of("/api/acl/v1/one", "/api/acl/v1/oneMore", "/api/acl/v1/anypath/two",
			"/api/acl/v1/anypath/two/more", "/api/acl/v1/some-path-var/three",
			"/api/acl/v1/some-path-var/three/and-more", "/api/acl/v1/different-path-var/three",
			"/api/acl/v1/different-path-var/three/and-more", "/api/acl/v1/anypath/four",
			"/api/acl/v1/anypath/four-more", "/api/acl/v1/five", "/api/acl/v1/fiverrrr", "/api/acl/v1/six",
			"/api/acl/v1/sixsox", "/api/acl/v1/somepath/morepath/three");

	@Test
	void testRegexOne() throws Exception {
		var regex = "/api/acl/v\\d{1,3}/one";

		for (int i = 0; i < uris.size(); i++) {
			if (Arrays.asList(0).contains(i)) {
				assertTrue(Pattern.matches(regex, uris.get(i)));
			} else {
				assertFalse(Pattern.matches(regex, uris.get(i)));
			}
		}
	}

	@Test
	void testRegexThree() throws Exception {
		var regex = "/api/acl/v\\d{1,3}/.*/three";

		for (int i = 0; i < uris.size(); i++) {
			if (Arrays.asList(4, 6, 14).contains(i)) {
				LOG.info("{}", i);
				assertTrue(Pattern.matches(regex, uris.get(i)));
			} else {
				assertFalse(Pattern.matches(regex, uris.get(i)));
			}
		}
	}

	@Test
	void testRegexTwoAndFour() throws Exception {
		var regex = "/api/acl/v\\d{1,3}/anypath/.*";

		for (int i = 0; i < uris.size(); i++) {
			if (Arrays.asList(2, 3, 8, 9).contains(i)) {
				assertTrue(Pattern.matches(regex, uris.get(i)));
			} else {
				assertFalse(Pattern.matches(regex, uris.get(i)));
			}
		}
	}

}
