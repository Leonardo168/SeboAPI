package com.Leonardo168.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		roles.add(new RoleModel(UUID.fromString("f36630dd-d521-4c74-ab3a-f2443bde992f"),RoleName.ROLE_USER));
		userModel.setRoles(roles);
		userModel.setEnable(true);
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
		return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
	}
	
	@DeleteMapping("/self")
	public ResponseEntity<Object> disableCurrentUser(){
		UserModel userModel = userService.findByUsername(userService.getCurrentUsername()).get();
		userModel.setEnable(false);
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("User disabled");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> disableUserById(@PathVariable(value = "id") UUID id){
		Optional<UserModel> userModelOptional = userService.findByID(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userModelOptional.get(), userModel);
		userModel.setEnable(false);
		userService.save(userModel);
		return ResponseEntity.status(HttpStatus.OK).body("User disabled");
	}
	
	/*PARA REMOVER USUÁRIOS DO BANCO DE DADOS*/
//	@DeleteMapping("admin/{id}")
//	public ResponseEntity<Object> deleteUserById(@PathVariable(value = "id") UUID id){
//		Optional<UserModel> userModelOptional = userService.findByID(id);
//		if(!userModelOptional.isPresent()) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//		}
//		UserModel userModel = new UserModel();
//		BeanUtils.copyProperties(userModelOptional.get(), userModel);
//		userService.delete(userModelOptional.get());
//		return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
//	}

}
