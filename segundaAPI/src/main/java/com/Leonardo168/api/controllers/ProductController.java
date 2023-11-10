package com.Leonardo168.api.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.dtos.ProductRecordDto;
import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.repositories.ProductRepository;
import com.Leonardo168.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserService userService;
	
	@PostMapping
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel);
		productModel.setAvailable(true);
		productModel.setEditDate(LocalDateTime.now(ZoneId.of("UTC")));
		productModel.setVendorId(userService.getCurrentUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}

}
