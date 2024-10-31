package com.nitp.club.management.controllers;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nitp.club.management.entities.CustomUser;
import com.nitp.club.management.models.CreateUserRequest;
import com.nitp.club.management.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RestControllerAdvice
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/createUser")
	public CustomUser createUser(@Validated @RequestBody CreateUserRequest request) {
		return userService.createUser(request);
	}
	
	@GetMapping("/me")
	public CustomUser findMe(HttpServletRequest request) {
		return userService.findMe();
	}
	
	@GetMapping("/all")
	public List<CustomUser> findAll(){
		return userService.findAll();
	}
}
