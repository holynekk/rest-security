package com.course.apisecurity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.course.apisecurity.entity.JwtData;
import com.course.apisecurity.service.JwtService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class JwtTest {

	@Autowired
	private JwtService service;

	static String jwt;

	@Test
	@Order(0)
	void testSign() throws Exception {
		var token = new JwtData();
		token.setUsername("Username");
		token.setDummyAttribute("Dummy");

		jwt = service.store(token);

		System.out.println(jwt);

		assertNotNull(jwt);
	}

//	@Test
//	@Order(1)
	void testRead() throws Exception {
		var x = service.read(jwt);

		assertTrue(x.isPresent());
	}

}
