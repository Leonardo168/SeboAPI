package com.Leonardo168.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.repositories.ProductRepository;

public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	Optional<ProductModel> findByIsbn(String isbn){
		return productRepository.findByIsbn(isbn);
	}

}
