package com.Leonardo168.api.controllers;

import java.util.Optional;

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

import com.Leonardo168.api.dtos.CategoryRecordDto;
import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.models.ProductModel;
import com.Leonardo168.api.services.CategoryService;
import com.Leonardo168.api.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@PostMapping
	public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryRecordDto categoryRecordDto){
		if(categoryService.existsByname(categoryRecordDto.categoryName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category name is already in use!");
		}
		CategoryModel categoryModel = new CategoryModel();
		BeanUtils.copyProperties(categoryRecordDto, categoryModel);
		categoryModel.setEnable(true);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryModel));
	}

	@GetMapping
	public ResponseEntity<Page<CategoryModel>> getAllCategories(@PageableDefault(page = 0, size = 10, sort = "categoryName", direction = Sort.Direction.ASC)Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageable));
	}

	@PutMapping("/{categoryName}")
	public ResponseEntity<Object> updateCategoryByName(@PathVariable(value = "categoryName") String categoryName, @RequestBody @Valid CategoryRecordDto categoryRecordDto){
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(categoryName);
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		if(productService.existsByCategory(categoryModelOptional.get())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot edit categories with registered products.");
		}
		if(categoryService.existsByname(categoryRecordDto.categoryName()) && !categoryRecordDto.categoryName().equals(categoryModelOptional.get().getCategoryName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category name is already in use!");
		}
		CategoryModel categoryModel = categoryModelOptional.get();
		BeanUtils.copyProperties(categoryRecordDto, categoryModel);
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.save(categoryModel));
	}

	@DeleteMapping("/{categoryName}")
	public ResponseEntity<Object> disableCategoryByName(@PathVariable(value = "categoryName") String categoryName){
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(categoryName);
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		if(!categoryModelOptional.get().isEnable()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Category is already disabled");
		}
		CategoryModel categoryModel = categoryModelOptional.get();
		categoryModel.setEnable(false);
		categoryService.save(categoryModel);
		return ResponseEntity.status(HttpStatus.OK).body("Category disabled");
	}

	@DeleteMapping("/definitivo/{categoryName}")
	public ResponseEntity<Object> deleteCategoryByName(@PathVariable(value = "categoryName") String categoryName){
		Optional<CategoryModel> categoryModelOptional = categoryService.findByCategoryName(categoryName);
		if(!categoryModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
		}
		Optional<Page<ProductModel>> productModelOptional = productService.findByCategory(categoryModelOptional.get(), null);
		if(!productModelOptional.get().isEmpty()){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete categories with registered products.");
		}
		categoryService.delete(categoryModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Category deleted.");
	}

}
