package com.deimos.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.deimos.dto.UserDto;
import com.deimos.entities.Users;
import com.deimos.repository.UserRepository;
import com.deimos.services.UserService;
import com.deimos.utils.DataSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class VerifyUserFields {

	@Resource
	private UserRepository userRepository;
	
	@Resource private UserService userService; 

	@Autowired
	private MockMvc mvc;

	@Test
	public void verifyIfUsernameExists() throws Exception {

		UserDto user = new UserDto();

		user.setEmail("john.dor@test.com");
		user.setPassword("Test@123");
		user.setUsername("John-Doe");

		userService.signUp(user);

		Users userFinded = userRepository.findByUsername(user.getUsername());
		Boolean usernameExists = userFinded == null;

		assertEquals(false, usernameExists);
	}

	@Test
	public void verifyIfEmailIsValid() throws Exception {
		MockHttpServletResponse result = mvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new Users("John Doe", "Test@123", "john.doe@test"))))
				.andReturn().getResponse();

		assertEquals(400, result.getStatus());
		assertEquals("Email inválido.", result.getContentAsString());
	}

	@Test
	public void verifyIfPasswordValid() throws Exception {
		MockHttpServletResponse result = mvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new Users("John Doe", "123456", "john.doe@test.com"))))
				.andReturn().getResponse();

		assertEquals(400, result.getStatus());
		assertEquals("Senha inválida, favor verificar padrão.", result.getContentAsString());
	}

	@Test
	public void givenString_whenEncrypt_thenSuccess() throws NoSuchAlgorithmException, IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

		String input = "baeldung";
		SecretKey key = DataSecurity.generateKey(128);
		IvParameterSpec ivParameterSpec = DataSecurity.generateIv();
		String cipherText = DataSecurity.encrypt(input, key, ivParameterSpec);
		String plainText = DataSecurity.decrypt(cipherText, key, ivParameterSpec);
		assertEquals(input, plainText);
	}

	@Test
	public void ifPasswordWasDecrypted() throws Exception {
		UserDto user = new UserDto();

		user.setEmail("john.dor@test.com");
		user.setPassword("Test@123");
		user.setUsername("John-Doe");

		userService.signUp(user);
		
		Users userRetrived = userRepository.findByUsername("John-Doe");
		
		String decryptedPassword = DataSecurity.decrypt(userRetrived.getPassword(), new SecretKeySpec(userRetrived.getKey(), 0, userRetrived.getKey().length, "AES"), new IvParameterSpec(userRetrived.getIv()));
		
		assertEquals("Test@123", decryptedPassword);
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
