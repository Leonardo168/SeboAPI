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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.dtos.ProductRecordDto;
import com.Leonardo168.api.enums.RoleName;
import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.models.RoleModel;
import com.Leonardo168.api.services.CategoryService;
import com.Leonardo168.api.services.ProductService;
import com.Leonardo168.api.services.TransactionService;
import com.Leonardo168.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	TransactionService transactionService;

	@GetMapping
	public ResponseEntity<Page<ProductModel>> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity <Object> getProductByTitle(@PathVariable(value = "title") String title, @PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		Optional<Page<Object>> productModelOptional = productService.findByTitle(title, pageable);
		if(productModelOptional.get().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for title: " + title);
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}

	@GetMapping("/isbn/{isbn}")
	public ResponseEntity <Object> getProductByIsbn(@PathVariable(value = "isbn") String isbn, @PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		Optional<Page<Object>> productModelOptional = productService.findByIsbn(isbn, pageable);
		if(productModelOptional.get().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for ISBN: " + isbn);
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}
	
	@GetMapping("/author/{author}")
	public ResponseEntity <Object> getProductByAuthor(@PathVariable(value = "author") String author, @PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		Optional<Page<Object>> productModelOptional = productService.findByAuthor(author, pageable);
		if(productModelOptional.get().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for author: " + author);
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}

	@GetMapping("/category/{category}")
	public ResponseEntity <Object> getProductByCategory(@PathVariable(value = "category") String category, @PageableDefault(page = 0, size = 10, sort = "title", direction = Sort.Direction.ASC)Pageable pageable){
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(category);
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		Optional<Page<ProductModel>> productModelOptional = productService.findByCategory(categoryModelOptional.get(), pageable);
		if(productModelOptional.get().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for category: " + category.toUpperCase());
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}

	@PostMapping
	public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(productRecordDto.category());
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		if(!categoryModelOptional.get().isEnable()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category " + categoryModelOptional.get().getCategoryName() + " is disabled.");
		}
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel, productRecordDto.category());
		productModel.setCategory(categoryModelOptional.get());
		productModel.setAvailable(true);
		productModel.setEditDate(LocalDateTime.now(ZoneId.of("UTC")));
		productModel.setVendorId(userService.getCurrentUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
	}

	@PutMapping("/{id}")
	public ResponseEntity <Object> updadateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){
		Optional<ProductModel> productModelOptional = productService.findById(id);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		if(!userService.findByID(userService.getCurrentUserId()).get().getRoles().contains(new RoleModel(UUID.fromString("0c5d4f9a-51cb-48ba-ac18-745b87b5cb10"),RoleName.ROLE_ADMIN))
				&& (!userService.getCurrentUserId().equals(productModelOptional.get().getVendorId()))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot edit other users products");
		}
		if (!productModelOptional.get().isAvailable()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot edit sold product");
		}
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(productRecordDto.category());
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		if(!categoryModelOptional.get().isEnable()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category " + categoryModelOptional.get().getCategoryName() + " is disabled.");
		}
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(productModelOptional.get(), productModel);
		BeanUtils.copyProperties(productRecordDto, productModel, productRecordDto.category());
		productModel.setCategory(categoryModelOptional.get());
		productModel.setEditDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
	}
	
	@DeleteMapping("/definitivo/{id}")
	public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") UUID id){
		Optional<ProductModel> productModelOptional = productService.findById(id);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		if (transactionService.existsByProductId(id)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete products with registered transactions.");
		}
		productService.delete(productModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted.");
	}

}
