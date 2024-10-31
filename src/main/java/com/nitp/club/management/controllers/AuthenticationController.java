package com.nitp.club.management.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nitp.club.management.models.LoginRequest;
import com.nitp.club.management.models.LoginResponse;
import com.nitp.club.management.services.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RestControllerAdvice
@RequestMapping("/auth")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@GetMapping
	public String testAuth() {
		return "Success";
	}
	
	@PostMapping("/login")
	public LoginResponse login(@Validated @RequestBody LoginRequest loginRequest, HttpServletResponse response, HttpServletRequest request) {
		return authenticationService.login(loginRequest, request, response);
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		return authenticationService.logout(request, response);
	}
}
