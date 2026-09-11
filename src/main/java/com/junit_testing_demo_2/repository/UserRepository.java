package com.junit_testing_demo_2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junit_testing_demo_2.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	
}
