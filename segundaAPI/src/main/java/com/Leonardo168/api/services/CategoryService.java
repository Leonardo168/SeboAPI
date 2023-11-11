package com.Leonardo168.api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Transactional
	public CategoryModel save(CategoryModel categoryModel) {
		return categoryRepository.save(categoryModel);
	}
	
	public Page<CategoryModel> findAll(Pageable pageable){
		return categoryRepository.findAll(pageable);
	}
	
	public Optional<CategoryModel> findById(UUID id){
		return categoryRepository.findById(id);
	}

	public boolean existsByname(String categoryName) {
		return categoryRepository.existsByCategoryName(categoryName);
	}

	public Optional<CategoryModel> findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName.toUpperCase());
	}
}
