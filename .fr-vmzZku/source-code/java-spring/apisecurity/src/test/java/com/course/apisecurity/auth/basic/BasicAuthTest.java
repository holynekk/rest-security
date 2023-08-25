package com.course.apisecurity.auth.basic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.course.apisecurity.entity.BasicAuthUser;
import com.course.apisecurity.repository.BasicAuthUserRepository;
import com.course.apisecurity.repository.JdbcCustomerCrudRepository;

@SpringBootTest
class BasicAuthTest {

	@Autowired
	private BasicAuthUserRepository repository;

	@Autowired
	private JdbcCustomerCrudRepository customerRepository;

	@Test
	void testCreateUser() throws Exception {
		var user = new BasicAuthUser();

		user.setPasswordHash("passwordHash" + RandomStringUtils.randomAlphanumeric(6));
		user.setSalt("salt" + RandomStringUtils.randomAlphanumeric(6));
		user.setUsername("username" + RandomStringUtils.randomAlphanumeric(6));

		assertNotNull(repository.save(user));
	}

	@Test
	void testCreateCustomer() throws Exception {
		var customer = new com.course.apisecurity.entity.JdbcCustomer();

		customer.setBirthDate(LocalDate.now());
		customer.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@garawew.com");
		customer.setFullName(RandomStringUtils.randomAlphanumeric(10));
		customer.setGender("M");

		assertNotNull(customerRepository.save(customer));
	}

}
