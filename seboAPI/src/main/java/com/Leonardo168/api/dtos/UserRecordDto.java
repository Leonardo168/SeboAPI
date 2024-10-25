package com.Leonardo168.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRecordDto(
		@NotBlank @Size(max = 70) String name,
		@NotBlank @Size(max = 20) String username,
		@NotBlank String password) {

}
