package com.Leonardo168.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.dtos.UserDto;
import com.Leonardo168.api.enums.RoleName;
import com.Leonardo168.api.models.RoleModel;
import com.Leonardo168.api.models.UserModel;
import com.Leonardo168.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity <Object> saveParkingSpot(@RequestBody @Valid UserDto userDto){
		if(userService.existsByUsername(userDto.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Username is already in use!");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userDto, userModel);
		List<RoleModel> roles = new ArrayList<>();
		roles.add(new RoleModel(UUID.fromString("f3bd1ddd-2b45-4dfd-be30-95fc4d21f97e"),RoleName.ROLE_USER));
		userModel.setRoles(roles);
		userModel.setEnable(true);
		userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}
	
	@GetMapping
	public ResponseEntity<List<UserModel>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	
	@PutMapping
	public ResponseEntity <Object> updadateUser(@RequestBody @Valid UserDto userDto){
		if(userService.existsByUsername(userDto.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Username is already in use!");
		}
		UserModel userModel = userService.findByUsername(userService.getCurrentUsername()).get();
		BeanUtils.copyProperties(userDto, userModel);
		userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
		return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
	}
	
	@PutMapping("/admin/{id}")
	public ResponseEntity <Object> setAdminRole(@PathVariable(value = "id") UUID id){
		Optional<UserModel> userModelOptional = userService.findByID(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userModelOptional.get(), userModel);
		List<RoleModel> roles = userModel.getRoles();
		roles.add(new RoleModel(UUID.fromString("0c5d4f9a-51cb-48ba-ac18-745b87b5cb10"),RoleName.ROLE_ADMIN));
		userModel.setRoles(roles);
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("The user has been given an admin role.");
	}
	
	@DeleteMapping("/self")
	public ResponseEntity<Object> disableCurrentUser(){
		UserModel userModel = userService.findByUsername(userService.getCurrentUsername()).get();
		List<RoleModel> roles = new ArrayList<>();
		roles.add(new RoleModel(UUID.fromString("356ec2b4-f9f2-4700-a4eb-43cc3229e873"),RoleName.ROLE_DISABLED));
		userModel.setRoles(roles);
		userModel.setEnable(false);
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("User disabled");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> disableUserById(@PathVariable(value = "id") UUID id){
		if (id.equals(UUID.fromString("eae7e721-05ee-4c59-95a5-e4a845c2ad8e"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("cannot disable default admin");
		}
		Optional<UserModel> userModelOptional = userService.findByID(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userModelOptional.get(), userModel);
		List<RoleModel> roles = new ArrayList<>();
		roles.add(new RoleModel(UUID.fromString("356ec2b4-f9f2-4700-a4eb-43cc3229e873"),RoleName.ROLE_DISABLED));
		userModel.setRoles(roles);
		userModel.setEnable(false);
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("User disabled");
	}
	
	/*PARA REMOVER USUÁRIOS DO BANCO DE DADOS*/
	@DeleteMapping("definitivo/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable(value = "id") UUID id){
		if (id.equals(UUID.fromString("eae7e721-05ee-4c59-95a5-e4a845c2ad8e"))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("cannot delete default admin");
		}
		Optional<UserModel> userModelOptional = userService.findByID(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userModelOptional.get(), userModel);
		userService.delete(userModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
	}

}
