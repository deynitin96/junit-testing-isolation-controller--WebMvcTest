package com.junit_testing_demo_2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junit_testing_demo_2.entity.User;
import com.junit_testing_demo_2.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUsername(@PathVariable long id){
		
		String name = userService.getUsername(id);
		
		if(name.equals("Unknown User")) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(name);
	}
	
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user){
		
		User savedUser = userService.createUser(user);
		
		return ResponseEntity.status(201).body(savedUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user){
		
		Optional<User> updatedUser = userService.updateUser(id, user);
		
		return updatedUser.map(ResponseEntity::ok)
							.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id){
		
		boolean status = userService.deleteUser(id);
		
		return status ? ResponseEntity.noContent().build()
				                     : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<?> getAllUsers(){
		
		List<User> users = userService.findAllUsers();
		
		if(users.isEmpty()) {
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(users);
	}

}
