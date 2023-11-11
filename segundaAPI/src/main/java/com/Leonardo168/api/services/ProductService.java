package com.Leonardo168.api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.repositories.ProductRepository;

import jakarta.transaction.Transactional;

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
	
	public Optional<ProductModel> findByIsbn(String isbn){
		return productRepository.findByIsbn(isbn);
	}

	public Optional<ProductModel> findById(UUID id) {
		return productRepository.findById(id);
	}

}
