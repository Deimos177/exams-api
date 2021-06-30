package com.deimos.services;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.deimos.dto.UserDto;
import com.deimos.entities.Users;
import com.deimos.repository.UserRepository;
import com.deimos.utils.DataSecurity;

@Service
public class UserService {

	final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

	@Autowired
	UserRepository userRepository;

	public ResponseEntity<String> signUp(UserDto user) throws Exception {

		Users userToSave = new Users();

		Matcher validEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
		Matcher validPassword = VALID_PASSWORD_REGEX.matcher(user.getPassword());
		Boolean usernameExists = userRepository.findByUsername(user.getUsername()) != null;

		if (Boolean.FALSE.equals(validEmail.find())) {
			return ResponseEntity.badRequest().body("Email inválido.");
		} else if (Boolean.FALSE.equals(validPassword.find())) {
			return ResponseEntity.badRequest().body("Senha inválida, favor verificar padrão.");
		} else if (Boolean.TRUE.equals(usernameExists)) {
			return ResponseEntity.badRequest().body("Nome de usuário existente.");
		}

		SecretKey key = DataSecurity.generateKey(128);
		IvParameterSpec iv = DataSecurity.generateIv();
		String cipherTextPassword = DataSecurity.encrypt(user.getPassword(), key, iv);

		userToSave.setEmail(user.getEmail());
		userToSave.setUsername(user.getUsername());
		userToSave.setIv(iv.getIV());
		userToSave.setKey(key.getEncoded());
		userToSave.setPassword(cipherTextPassword);

		userRepository.save(userToSave);

		return ResponseEntity.created(null).build();
	}

	public ResponseEntity<String> removeUser(String id){
		
		Long userId = Long.parseLong(id);
		
		Optional<Users> user = userRepository.findById(userId);
		
		if(user.isEmpty()) {
			return ResponseEntity.status(400).build();
		}
		
		Users userToDelete = new Users(user.get());
		userRepository.delete(userToDelete);
		
		return ResponseEntity.status(204).build();
	}
}
