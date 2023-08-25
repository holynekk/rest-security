package com.course.apisecurity.api.server.auth.acl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acl/v1")
public class BasicAclApi {

	@GetMapping("/one")
	public String oneGet() {
		return "GET One";
	}

	@DeleteMapping("/one")
	public String oneDelete() {
		return "DELETE One";
	}

	@PostMapping("/anypath/two")
	public String two() {
		return "Two";
	}

	@PutMapping("/somepath/morepath/three")
	public String three() {
		return "Three";
	}

	@GetMapping("/anypath/evenmorepath/four")
	public String four() {
		return "Four";
	}

}
