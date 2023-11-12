package com.Leonardo168.api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Transactional
	public ProductModel save(ProductModel productModel) {
		return productRepository.save(productModel);
	}
	
	public Page<ProductModel> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}
	
	public Optional<Page<Object>> findByIsbn(String isbn, Pageable pageable){
		return productRepository.findByIsbn(isbn, pageable);
	}

	public Optional<ProductModel> findById(UUID id) {
		return productRepository.findById(id);
	}

	public Optional<Page<ProductModel>> findByCategory(CategoryModel categoryModel, Pageable pageable) {
		return productRepository.findByCategory(categoryModel, pageable);
	}

	public void delete(ProductModel productModel) {
		productRepository.delete(productModel);
	}

}
