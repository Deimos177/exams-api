package com.exams.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.exams.entities.Users;
import com.exams.repository.UserRepository;
import com.exams.views.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class UserIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Resource
	UserRepository userRepository;

	@Test
	public void signUpUser() throws Exception {

		MockHttpServletResponse result = mvc
				.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(new Users("John Doe", "Test@123", "john.doe@test.com"))))
				.andReturn().getResponse();

		assertEquals(201, result.getStatus());
	}

	@Test
	public void removeUser() throws Exception {

		MockHttpServletResponse result = mvc
				.perform(delete("/user/leaving/1").contentType(MediaType.APPLICATION_JSON).param("id", "1")).andReturn()
				.getResponse();

		assertEquals(204, result.getStatus());
	}
	
	@Test
	public void updatePassword() throws Exception{
		
		MockHttpServletResponse createResult = mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new Users("Jane Doe", "Jane@123", "jane.doe@test.com"))))
		.andReturn()
		.getResponse();
		
		assertEquals(201, createResult.getStatus());

		MockHttpServletResponse result = mvc
				.perform(put("/user/updatePassword/Jane Doe").contentType(MediaType.APPLICATION_JSON)
						.param("username", "Jane Doe")
						.content("Test@456")).andReturn()
				.getResponse();

		assertEquals(200, result.getStatus());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}