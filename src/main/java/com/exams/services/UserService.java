package com.exams.services;

import org.springframework.http.ResponseEntity;

import com.exams.dto.UserDto;
import com.exams.views.UserView;

public interface UserService {
	
	public ResponseEntity<UserView> signUp(UserDto user) throws Exception;
	public ResponseEntity<UserView> removeUser(String id);
	public ResponseEntity<UserView> createResetToken(String email) throws Exception;
	public ResponseEntity<UserView> resetPassword(String token, String password) throws Exception;
}