package com.Leonardo168.api.dtos;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDto(
		@NotBlank String title,
		@NotBlank String author,
		@NotBlank String categorie,
		@NotNull BigDecimal value,
		@NotBlank @Length(max = 255) String description) {
}
