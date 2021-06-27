package com.deimos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deimos.entities.Users;
import com.deimos.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody Users user) {

		ResponseEntity<String> result = userService.signUp(user);
		
		return result;
	}
}
