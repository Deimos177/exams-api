package com.exams.services;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.exams.dto.UserDto;
import com.exams.entities.User;
import com.exams.repository.UserRepository;
import com.exams.utils.DataSecurity;
import com.exams.utils.EmailService;
import com.exams.views.UserView;

@Service
public class UserService {

	final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	public ResponseEntity<UserView> signUp(UserDto user) throws Exception {

		User userToSave = new User();
		UserView response = new UserView();

		Matcher validEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
		Matcher validPassword = VALID_PASSWORD_REGEX.matcher(user.getPassword());
		Boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();

		if (Boolean.FALSE.equals(validEmail.find())) {

			response.setError(true);
			response.setMessage("Email inválido.");
			return ResponseEntity.badRequest().body(response);
		} else if (Boolean.FALSE.equals(validPassword.find())) {

			response.setError(true);
			response.setMessage("Senha inválida.");
			return ResponseEntity.badRequest().body(response);
		} else if (Boolean.TRUE.equals(usernameExists)) {

			response.setError(true);
			response.setMessage("Nome de usuário existente.");
			return ResponseEntity.badRequest().body(response);
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

		response.setEmail(userToSave.getEmail());
		response.setUsername(userToSave.getUsername());
		response.setError(false);

		return ResponseEntity.created(null).body(response);
	}

	public ResponseEntity<UserView> removeUser(String id) {

		Optional<User> user = userRepository.findById(Long.parseLong(id));
		UserView response = new UserView();

		if (user.isEmpty()) {
			response.setError(true);
			response.setMessage("Usuário não encontrado");
			return ResponseEntity.status(404).body(response);
		}

		User userToDelete = new User(user.get());
		userRepository.delete(userToDelete);

		response.setError(false);

		return ResponseEntity.status(204).body(response);
	}

	public ResponseEntity<UserView> createResetToken(String email) throws Exception {

		Optional<User> user = userRepository.findByEmail(email);
		UserView response = new UserView();

		if (user.isEmpty()) {
			response.setError(true);
			response.setMessage("Usuário não encontrado");
			return ResponseEntity.status(404).body(response);
		}

		User userToUpdate = user.get();

		userToUpdate.setToken(UUID.randomUUID().toString());

		userRepository.save(userToUpdate);

		SimpleMailMessage passwordResetEmail = emailService.generatePasswordEmail(email, userToUpdate.getToken());

		emailService.sendEmail(passwordResetEmail);

		response.setMessage("An email sent to you with link to reset your password");
		response.setError(false);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<UserView> resetPassword(String token, String password) throws Exception {

		Optional<User> userGeted = userRepository.findByResetToken(token);
		UserView response = new UserView();

		if (userGeted.isEmpty()) {
			response.setError(true);
			response.setMessage("Token inválido.");
			return ResponseEntity.status(404).body(response);
		}

		User user = userGeted.get();

		Matcher validPassword = VALID_PASSWORD_REGEX.matcher(user.getPassword());

		if (Boolean.FALSE.equals(validPassword.find())) {
			response.setError(true);
			response.setMessage("Senha inválida.");
			return ResponseEntity.status(404).body(response);
		}

		SecretKey key = DataSecurity.generateKey(128);
		IvParameterSpec iv = DataSecurity.generateIv();
		String cipherTextPassword = DataSecurity.encrypt(password, key, iv);
		user.setIv(iv.getIV());
		user.setKey(key.getEncoded());
		user.setPassword(cipherTextPassword);
		user.setToken(null);
		
		userRepository.save(user);

		response.setError(false);
		response.setMessage("Senha atualizada com sucesso.");

		return ResponseEntity.ok(response);
	}
}
