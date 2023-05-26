package com.crud.app.rest;

import com.crud.app.rest.Controllers.ApiControllers;
import com.crud.app.rest.Models.User;
import com.crud.app.rest.Repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(ApiControllers.class)
class RestApiApplicationTests {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	UserRepo userRepo;

	User USER1 = new User(1L, "Park", "Yon", "Developer", 26);
	User USER2 = new User(2L, "Park", "Bo Gum", "Developer", 28);
	User USER3 = new User(3L, "Son", "Ye Jin", "Developer", 40);
	User USER4 = new User(4L, "Kim", "Da Mi", "Developer", 26);

	private final String GET_ALL_USER = "/users";
	private final String CREATE_USER = "/user/create";
	private final String UPDATE_USER = "/user/update/1";
	private final String DELETE_USER = "/user/delete/2";

	@Test()
	@Order(1)
	void getUserList() throws Exception {
		List<User> records = new ArrayList<>(Arrays.asList(USER1, USER2, USER3, USER4));

		Mockito.when(userRepo.findAll()).thenReturn(records);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(GET_ALL_USER)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(request)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(4)))
					.andExpect(jsonPath("$[1].first_name", Matchers.is("Park")))
					.andReturn();

		int status = mvcResult.getResponse().getStatus();
		String body = mvcResult.getResponse().getContentAsString();
//		System.out.println("Statusssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss "+ objectMapper.readValue(body, new TypeReference<List<User>>(){}).get(1).getFirst_name());
		assertEquals(200, status);
	}

	@Test
	@Order(2)
	void createUser() throws Exception {
		User userToCreate = new User(5L, "Bo", "Young", "Actress", 35);

		Mockito.when(userRepo.save(userToCreate)).thenReturn(userToCreate);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(CREATE_USER)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userToCreate));

		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
//				.andExpect(jsonPath("$.first_name", Matchers.is("Bo")))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(3)
	void updateUser() throws Exception {
		User userToUpdate = new User(1L, "Park", "Bo Young", "Actress", 35);

		Mockito.when(userRepo.findById(USER1.getId())).thenReturn(Optional.of(USER1));
		Mockito.when(userRepo.save(userToUpdate)).thenReturn(userToUpdate);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(UPDATE_USER)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userToUpdate));

		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
//				.andExpect(jsonPath("$.first_name", Matchers.is("Bo")))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(4)
	void deleteUser() throws Exception {
		Mockito.when(userRepo.findById(USER2.getId())).thenReturn(Optional.of(USER2));

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.delete(DELETE_USER)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

}
