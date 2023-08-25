package com.course.apisecurity.sqlinjection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import com.course.apisecurity.entity.JdbcCustomer;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class JdbcCustomerDangerApiTest {

	private final String BASE_PATH = "/api/danger/v1";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${spring.datasource.username}")
	private String postgresUser;

	@Test
	@Order(5)
	void deleteDummy() {
		assertDoesNotThrow(
				() -> jdbcTemplate.update("delete from jdbc_customer where email = ?", "diana.prince@dc.com"));
	}

	@Test
	@Order(3)
	void testCreateCustomer() throws Exception {
		var newCustomer = new JdbcCustomer();
		newCustomer.setBirthDate(LocalDate.of(2000, 12, 31));
		newCustomer.setEmail("diana.prince@dc.com");
		newCustomer.setFullName("Diana Prince");
		newCustomer.setGender("F");

		var requestBody = objectMapper.writeValueAsString(newCustomer);

		var testPerform = mockMvc.perform(
				post(BASE_PATH + "/customer").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));
		testPerform.andExpect(status().isOk());

		// check data
	}

	@Test
	@Order(2)
	void testFindCustomerByEmail() throws Exception {
		var email = "mdonald0@marriott.com";

		var testPerform = mockMvc.perform(get(BASE_PATH + "/customer/" + email));
		testPerform.andExpect(status().isOk());

		var responseBody = testPerform.andReturn().getResponse().getContentAsString();
		assertNotNull(responseBody);

		var testData = objectMapper.readValue(responseBody, JdbcCustomer.class);
		assertNotNull(testData);
		assertEquals(email, testData.getEmail());
	}

	@Test
	@Order(4)
	void testFindCustomerByEmail_AfterTestCreate() throws Exception {
		var email = "diana.prince@dc.com";

		var testPerform = mockMvc.perform(get(BASE_PATH + "/customer/" + email));
		testPerform.andExpect(status().isOk());

		var responseBody = testPerform.andReturn().getResponse().getContentAsString();
		assertNotNull(responseBody);

		var testData = objectMapper.readValue(responseBody, JdbcCustomer.class);
		assertNotNull(testData);
		assertEquals(email, testData.getEmail());
	}

	@Test
	@Order(1)
	void testFindCustomersByGender() throws Exception {
		var testPerform = mockMvc.perform(get(BASE_PATH + "/customer/gender").param("genderCode", "F"));
		testPerform.andExpect(status().isOk());

		var responseBody = testPerform.andReturn().getResponse().getContentAsString();
		assertNotNull(responseBody);

		var testList = objectMapper.readValue(responseBody, JdbcCustomer[].class);
		assertEquals(7, testList.length);
	}

	@Test
	@Order(6)
	void testFindCustomersForSqlInjection() throws Exception {
		var sqlInjectionParam = "M' ; drop table jdbc_merchant;--";
		var testPerform = mockMvc.perform(get(BASE_PATH + "/customer/gender").param("genderCode", sqlInjectionParam));

		if (postgresUser.equals("postgres")) {
			assertThrows(RuntimeException.class,
					() -> jdbcTemplate.queryForObject("select merchant_id from jdbc_merchant limit 1", Long.class));
		} else if (postgresUser.equals("postgres_limited")) {
			testPerform.andExpect(status().is5xxServerError());
			assertDoesNotThrow(
					() -> jdbcTemplate.queryForObject("select merchant_id from jdbc_merchant limit 1", Long.class));
		} else {
			fail("Username postgres " + postgresUser + " not defined in test scope");
		}
	}
}
