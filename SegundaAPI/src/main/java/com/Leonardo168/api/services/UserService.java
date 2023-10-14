package com.Leonardo168.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Leonardo168.api.models.UserModel;
import com.Leonardo168.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}
	
	public List<UserModel> findAll() {
		return userRepository.findAll();
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

}
