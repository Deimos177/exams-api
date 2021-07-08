package com.deimos.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deimos.dto.ResetPasswordRequest;
import com.deimos.dto.UserDto;
import com.deimos.services.UserService;
import com.deimos.views.UserView;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserView> signUp(@RequestBody UserDto user) throws Exception {

		return userService.signUp(user);
	}

	@DeleteMapping("/leaving/{id}")
	public ResponseEntity<UserView> leaving(@PathParam(value = "id") String id) {

		return userService.removeUser(id);
	}

	@GetMapping("/forgot/{email}")
	public ResponseEntity<UserView> sendEmailToResetPassword(@PathVariable(value = "email") String email)
			throws Exception {

		return userService.createResetToken(email);
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<UserView> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest password)
			throws Exception {
		return userService.resetPassword(token, password.getPassword());
	}
}