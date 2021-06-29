package com.deimos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deimos.dto.UserDto;
import com.deimos.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody UserDto user) throws Exception {
	
		return userService.signUp(user);
	}
}