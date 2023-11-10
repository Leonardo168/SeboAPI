package com.Leonardo168.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
	
	@NotBlank
	private String name;
	@NotBlank
	private String username;
	@NotBlank
	private String password;

}
