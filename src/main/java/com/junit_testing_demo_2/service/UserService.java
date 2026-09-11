package com.junit_testing_demo_2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.junit_testing_demo_2.repository.UserRepository;
import com.junit_testing_demo_2.entity.User;

@Service
public class UserService {
	
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String getUsername(long id) {
		
		return userRepository.findById(id)
					  .map(User::getName)
					  .orElse("Unknown Users");
		
	}

	public User createUser(User user) {
		
		return userRepository.save(user);
		
	}

	public Optional<User> updateUser(long id, User user) {
		
		return userRepository.findById(id)
				             .map(existing -> userRepository.save(new User(id, user.getName())));
		
	}

	public boolean deleteUser(long id) {
		
		if(userRepository.findById(id).isPresent()) {
			
			userRepository.deleteById(id);
			
			return true;
		}
		
		return false;
		
	}

	public List<User> findAllUsers() {
		
		return userRepository.findAll();
		
	}

	

	

}
