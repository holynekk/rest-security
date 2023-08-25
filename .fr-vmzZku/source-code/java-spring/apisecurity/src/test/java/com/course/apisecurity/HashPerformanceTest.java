package com.course.apisecurity;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.course.apisecurity.util.HashUtil;
import com.course.apisecurity.util.SecureStringUtil;

class HashPerformanceTest {

	static final double LOOP = 2_000d;

	static String salt = "The16BytesSalt99";
	static String original = "The original plain string";
	static String hash;

	static String stringOne = "TheStringToBeComparedThatQuiteLongButYouCanReadIt";
	static String stringTwo = "TheStringToBeComparedThatQuiteLongButYouCanReadIt";

	@BeforeAll
	static void beforeAll() {
		hash = HashUtil.bcrypt(original, salt);
	}

	@Test
	void testHash() {
		var start = System.currentTimeMillis();

		for (var i = 0; i < LOOP; i++) {
			HashUtil.isBcryptMatch(original, hash);
		}

		var end = System.currentTimeMillis();

		var total = end - start;

		System.out.println("BCRYPT");
		System.out.println("   Total (ms) : " + total);
		System.out.println("   Average (ms) : " + (total / LOOP));
	}

	@Test
	void testEquals() {
		var start = System.currentTimeMillis();

		for (var i = 0; i < LOOP; i++) {
			StringUtils.equals(stringTwo, stringOne);
		}

		var end = System.currentTimeMillis();

		var total = end - start;

		System.out.println("Equals");
		System.out.println("   Total (ms) : " + total);
		System.out.println("   Average (ms) : " + (total / LOOP));
	}

	@Test
	void testSecureEquals() {
		var start = System.currentTimeMillis();

		for (var i = 0; i < LOOP; i++) {
			SecureStringUtil.equals(stringTwo, stringOne);
		}

		var end = System.currentTimeMillis();

		var total = end - start;

		System.out.println("Secure Equals");
		System.out.println("   Total (ms) : " + total);
		System.out.println("   Average (ms) : " + (total / LOOP));
	}

}
