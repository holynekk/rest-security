package com.course.apisecurity.acl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.course.apisecurity.entity.BasicAclUri;
import com.course.apisecurity.entity.BasicAclUserUriRef;
import com.course.apisecurity.entity.BasicAuthUser;
import com.course.apisecurity.repository.BasicAclUriRepository;
import com.course.apisecurity.repository.BasicAuthUserRepository;

@SpringBootTest
class BasicAclTest {

	@Autowired
	private BasicAclUriRepository uriRepository;

	@Autowired
	private BasicAuthUserRepository userRepository;

	@Test
	void testCreateBasicUri() {
		BasicAclUri data = new BasicAclUri();

		data.setUri("test" + System.currentTimeMillis());
		assertNotNull(uriRepository.save(data));
	}

	@Test
	void testCreateBasicUserNoUriRef() {
		BasicAuthUser data = new BasicAuthUser();
		var randomString = RandomStringUtils.randomAlphanumeric(5);

		data.setDisplayName("Name" + randomString);
		data.setPasswordHash("Password" + randomString);
		data.setSalt("Salt" + randomString);
		data.setUsername("Username" + randomString);

		assertNotNull(userRepository.save(data));
	}

	@Test
	void testCreateBasicUserWithUriRef() {
		BasicAuthUser data = new BasicAuthUser();
		var randomString = RandomStringUtils.randomAlphanumeric(8);

		data.setDisplayName("Name" + randomString);
		data.setPasswordHash("Password" + randomString);
		data.setSalt("Salt" + randomString);
		data.setUsername("Username" + randomString);

		Set<BasicAclUserUriRef> allowedUris = new HashSet<>();

		BasicAclUserUriRef ref1 = new BasicAclUserUriRef();
		BasicAclUserUriRef ref2 = new BasicAclUserUriRef();

		ref1.setUriId(1);
		ref2.setUriId(2);

		allowedUris.add(ref1);
		allowedUris.add(ref2);

		data.setAllowedUris(allowedUris);

		var saved = userRepository.save(data);
		assertNotNull(saved);
	}

	@Test
	void testGetUserWithUriRef() {
		var existing = userRepository.findById(2);

		assertTrue(existing.isPresent());
		assertFalse(existing.get().getAllowedUris().isEmpty());
	}

}
