package com.Leonardo168.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		roles.add(new RoleModel(UUID.fromString("a4f83c84-5688-4fa6-b0f6-44b9b33a11cb"),RoleName.ROLE_USER));
		userModel.setRoles(roles);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}
	
	@GetMapping
	public ResponseEntity<List<UserModel>> getAllUsers(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}

}
