package com.Leonardo168.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	public Optional<UserModel> findByID(UUID id) {
		return userRepository.findById(id);
	}
	
	public Optional<UserModel> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}else{
		    return "No User";
		}
	}

	public void delete(UserModel userModel) {
		userRepository.delete(userModel);
	}

}
