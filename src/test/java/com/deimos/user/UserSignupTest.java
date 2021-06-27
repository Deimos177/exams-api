package com.deimos.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.deimos.entities.Users;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class UserSignupTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private Users user;

	@Test
	public void signUpUser() throws Exception {
		
		user.setEmail("john.doe@test.com");
		user.setPassword("123456");
		user.setUsername("John Doe");
		
		MockHttpServletResponse result = mvc.perform(post("/user/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content("test"))
				.andReturn()
				.getResponse();
		
		assertEquals(201, result.getStatus());
	}
}