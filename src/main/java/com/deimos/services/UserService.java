package com.deimos.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deimos.entities.Users;
import com.deimos.repository.UserRepository;

@Service
public class UserService {
	
	final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	
	final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
			Pattern.CASE_INSENSITIVE);

	
	
	@Autowired
	UserRepository userRepository;

	public ResponseEntity<String> signUp(Users user) {
		
		Matcher validEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
		Matcher validPassword = VALID_PASSWORD_REGEX.matcher(user.getPassword());
		Boolean usernameExists = userRepository.findByUsername(user.getUsername()).getUsername().isBlank();
		
		if(Boolean.FALSE.equals(validEmail.find())) {
			ResponseEntity.badRequest().body("Email inválido.");
		}else if(Boolean.FALSE.equals(validPassword.find())) {
			ResponseEntity.badRequest().body("Senha inválida, favor verificar padrão.");
		}else if (Boolean.FALSE.equals(usernameExists)) {
			ResponseEntity.badRequest().body("Nome de usuário existente.");
		}
		
		userRepository.save(user);
		
		return ResponseEntity.created(null).build();
	}
}
