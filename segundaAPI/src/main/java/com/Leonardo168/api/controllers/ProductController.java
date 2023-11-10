package com.Leonardo168.api.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.dtos.ProductRecordDto;
import com.Leonardo168.api.enums.RoleName;
import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.models.RoleModel;
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
	
	@GetMapping
	public ResponseEntity<Page<ProductModel>> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll(pageable));
	}
	
	@GetMapping("/{isbn}")
	public ResponseEntity <Object> getProduct(@PathVariable(value = "isbn") String isbn){
		Optional<ProductModel> productModelOptional = productRepository.findByIsbn(isbn);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel);
		productModel.setAvailable(true);
		productModel.setEditDate(LocalDateTime.now(ZoneId.of("UTC")));
		productModel.setVendorId(userService.getCurrentUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity <Object> updadateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
		Optional<ProductModel> productModelOptional = productRepository.findById(id);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		if(!userService.findByID(userService.getCurrentUserId()).get().getRoles().contains(new RoleModel(UUID.fromString("0c5d4f9a-51cb-48ba-ac18-745b87b5cb10"),RoleName.ROLE_ADMIN))
				&& (!userService.getCurrentUserId().equals(productModelOptional.get().getVendorId()))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot edit other users products");
		}
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productModelOptional.get(), productModel);
		BeanUtils.copyProperties(productRecordDto, productModel);
		productModel.setEditDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}

}
