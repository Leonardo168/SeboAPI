package com.Leonardo168.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRecordDto(
		@NotBlank @Size(max = 20) String categoryName,
		@NotBlank @Size(max = 255) String categoryDescription) {
	
	public CategoryRecordDto(String categoryName, String categoryDescription) {
		this.categoryName = categoryName.toUpperCase();
		this.categoryDescription = categoryDescription;
	}
}
