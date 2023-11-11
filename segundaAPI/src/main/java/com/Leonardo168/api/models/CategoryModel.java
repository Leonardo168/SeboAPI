package com.Leonardo168.api.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_CATEGORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID categoryId;
	@Column(nullable = false, length = 20, unique = true)
	private String categoryName;
	@Column(nullable = false)
	private String categoryDescription;
	@Column(nullable = false)
	private boolean enable;

}
