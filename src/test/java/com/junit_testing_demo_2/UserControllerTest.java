package com.junit_testing_demo_2;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.*;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.junit_testing_demo_2.controller.UserController;
import com.junit_testing_demo_2.entity.User;
import com.junit_testing_demo_2.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	// DI
	@Autowired
	private MockMvc mockMvc;
	
	// MockitoBean
	@MockitoBean
	private UserService mockService;
	
	@Test
	void getUsername_found_returns200() throws Exception {
		
		when(mockService.getUsername(1)).thenReturn("Ram");
		
		mockMvc.perform(get("/users/1"))
			    .andExpect(status().isOk())
			    .andExpect(content().string("Ram"));
	}
	
	@Test
	void getUsername_notFound_returns200() throws Exception {
		
		when(mockService.getUsername(99)).thenReturn("Unknown User");
		
		mockMvc.perform(get("/users/99"))
			    .andExpect(status().isNotFound());
	}
	
	@Test
	void createUser_returns201() throws Exception {
		
		when(mockService.createUser(any(User.class)))
									        .thenReturn(new User(10, "Bob"));
		
		
		mockMvc.perform(post("/users")
		       .contentType(MediaType.APPLICATION_JSON)
		       .content("{\"id\":11,\"name\":\"Nitin\"}"))
			   .andExpect(status().isCreated())
			   .andExpect(jsonPath("$.id").value(10))
			   .andExpect(jsonPath("$.name").value("Bob"));
	}
	
	@Test
	void updateUser_found_returns200() throws Exception {
		
		when(mockService.updateUser(eq(10L), any(User.class)))
												.thenReturn(Optional.of(new User(10, "Charlie")));
		
		mockMvc.perform(put("/users/10")
		       .contentType(MediaType.APPLICATION_JSON)
		       .content("{\"id\":10,\"name\":\"Charlie\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(10))
				.andExpect(jsonPath("$.name").value("Charlie"));
												
	}
	
	@Test
	void updateUser_notFound_returns404() throws Exception {
		
		when(mockService.updateUser(eq(6), any(User.class)))
        								.thenReturn(Optional.empty());
		
		mockMvc.perform(put("/users/50")
			   .contentType(MediaType.APPLICATION_JSON)
		       .content("{\"id\":10,\"name\":\"Charlie\"}"))
			   .andExpect(status().isNotFound());
												
	}
	
	@Test
	void deleteUser_Deleted() throws Exception {
		
		when(mockService.deleteUser(1L))
									.thenReturn(true);
		
		mockMvc.perform(delete("/users/1"))
		       .andExpect(status().isNoContent());
		
	}
	
	@Test
	void deleteUser_notFound() throws Exception {
		
		when(mockService.deleteUser(20L))
								.thenReturn(false);
		
		mockMvc.perform(delete("/users/20"))
		       .andExpect(status().isNotFound());
	}
	
	 @Test
	void getAllUsers_Found() throws Exception {
		
		when(mockService.findAllUsers())
									.thenReturn(List.of(new User(1, "Ram"), new User(2, "Sam")));
		
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Ram"))
				.andExpect(jsonPath("$[1].name").value("Sam"));
	}
	 
	 @Test
	 void getAllUsers_noContent() throws Exception{
		 
		 when(mockService.findAllUsers())
		 							.thenReturn(Collections.emptyList());
		 
		 mockMvc.perform(get("/users"))
		 	    .andExpect(status().isNoContent());
	 }
}
