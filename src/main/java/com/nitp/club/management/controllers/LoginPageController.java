package com.nitp.club.management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
@RequestMapping("/login")
public class LoginPageController {

	@GetMapping
	public String loginPage() {
		return "login.html";
	}
}
