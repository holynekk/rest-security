package com.course.apisecurity.oauth.api.server;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/math")
public class MathApi {

	@GetMapping(value = "/random", produces = MediaType.TEXT_PLAIN_VALUE)
	public String random() {
		return "Random number : " + ThreadLocalRandom.current().nextInt();
	}

	@GetMapping(value = "/add", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_math:add')")
	public String add(@RequestParam(required = true, name = "a") int a,
			@RequestParam(required = true, name = "b") int b) {
		return "a + b = " + (a + b);
	}

	@GetMapping(value = "/subtract", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_math:subtract')")
	public String subtract(@RequestParam(required = true, name = "a") int a,
			@RequestParam(required = true, name = "b") int b) {
		return "a - b = " + (a - b);
	}

	@GetMapping(value = "/multiply", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_math:multiply')")
	public String multiply(@RequestParam(required = true, name = "a") int a,
			@RequestParam(required = true, name = "b") int b) {
		return "a * b = " + (a * b);
	}

	@GetMapping(value = "/divide", produces = MediaType.TEXT_PLAIN_VALUE)
	@PreAuthorize("hasAuthority('SCOPE_math:divide')")
	public String divide(@RequestParam(required = true, name = "a") int a,
			@RequestParam(required = true, name = "b") int b) {
		return "a / b = " + (a / b);
	}

}
