package com.course.apisecurity.oauth.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OktaController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

}
