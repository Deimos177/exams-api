package com.exams.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.exams.dto.ResetPasswordRequest;
import com.exams.dto.UserDto;
import com.exams.services.UserService;
import com.exams.views.UserView;

@RestController
@RequestMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerImpl{

	@Autowired
	UserService userService;

	@PostMapping(path = "/signup")
	public ResponseEntity<UserView> signUp(@RequestBody UserDto user) throws Exception {

		return userService.signUp(user);
	}

	@DeleteMapping(path = "/leaving/{id}")
	public ResponseEntity<UserView> leaving(@PathParam(value = "id") String id) {

		return userService.removeUser(id);
	}

	@GetMapping(path = "/forgot/{email}")
	public ResponseEntity<UserView> sendEmailToResetPassword(@PathVariable(value = "email") String email)
			throws Exception {

		return userService.createResetToken(email);
	}

	@PutMapping(path = "/resetpassword")
	public ResponseEntity<UserView> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordRequest password)
			throws Exception {
		return userService.resetPassword(token, password.getPassword());
	}
}