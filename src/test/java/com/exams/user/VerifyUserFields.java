package com.exams.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import javax.annotation.Resource;
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

import com.exams.dto.UserDto;
import com.exams.entities.User;
import com.exams.repository.UserRepository;
import com.exams.services.impl.UserServiceImpl;
import com.exams.utils.DataSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class VerifyUserFields {

	@Resource
	private UserRepository userRepository;
	
	@Resource private UserServiceImpl userService; 

	@Autowired
	private MockMvc mvc;

	@Test
	public void verifyIfUsernameExists() throws Exception {

		UserDto user = new UserDto();

		user.setEmail("john.dor@test.com");
		user.setPassword("Test@123");
		user.setUsername("John-Doe");

		userService.signUp(user);

		Optional<User> userFinded = userRepository.findByUsername(user.getUsername());
		Boolean usernameExists = userFinded.isPresent();

		assertEquals(false, usernameExists);
	}

	@Test
	public void verifyIfEmailIsValid() throws Exception {
		MockHttpServletResponse result = mvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new User("John Doe", "Test@123", "john.doe@test"))))
				.andReturn().getResponse();

		assertEquals(400, result.getStatus());
		assertEquals("Email inválido.", result.getContentAsString());
	}

	@Test
	public void verifyIfPasswordValid() throws Exception {
		MockHttpServletResponse result = mvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new User("John Doe", "123456", "john.doe@test.com"))))
				.andReturn().getResponse();

		assertEquals(400, result.getStatus());
		assertEquals("Senha inválida, favor verificar padrão.", result.getContentAsString());
	}

	@Test
	public void ifPasswordWasDecrypted() throws Exception {
		UserDto user = new UserDto();

		user.setEmail("john.dor@test.com");
		user.setPassword("Test@123");
		user.setUsername("John-Doe");

		userService.signUp(user);
		
		Optional<User> userRetrived = userRepository.findByUsername("John-Doe");
		User userReturn = userRetrived.get();
		
		String decryptedPassword = DataSecurity.decrypt(userReturn.getPassword(), new SecretKeySpec(userReturn.getKey(), 0, userReturn.getKey().length, "AES"), new IvParameterSpec(userReturn.getIv()));
		
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
