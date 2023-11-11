package com.Leonardo168.api.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(
		@NotBlank String categoryName,
		@NotBlank @Length(max = 255) String categoryDescription) {
	
	public CategoryRecordDto(String categoryName, String categoryDescription) {
		this.categoryName = categoryName.toUpperCase();
		this.categoryDescription = categoryDescription;
	}
}
