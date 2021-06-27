package com.deimos.user;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.deimos.entities.Users;
import com.deimos.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class VerifyUsernameTest {
	
	@Resource
	private UserRepository userRepository;

	@Test
	public void verifyIfUsernameExists() throws Exception {
		
		Users user = new Users();
		
		user.setEmail("john.dor@test.com");
		user.setPassword("Test@123");
		user.setUsername("John-Doe");
		
		userRepository.save(user);
		
		Boolean usernameExists = userRepository.findByUsername(user.getUsername()).getUsername().isBlank();
		
		assertEquals(false, usernameExists);
	}
}
