package com.course.apisecurity.auth.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RedisTokenTest {

	@Test
	void testAuthorizationHeader() throws Exception {
		var authorization = "Bearer 12345";

		assertEquals("12345", authorization.substring("Bearer".length() + 1));
	}

}
