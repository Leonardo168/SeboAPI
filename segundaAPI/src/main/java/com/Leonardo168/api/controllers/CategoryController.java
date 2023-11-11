package com.Leonardo168.api.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Leonardo168.api.dtos.CategoryRecordDto;
import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryRecordDto categoryRecordDto){
		if(categoryService.existsByname(categoryRecordDto.categoryName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Username is already in use!");
		}
		CategoryModel categoryModel = new CategoryModel();
		BeanUtils.copyProperties(categoryRecordDto, categoryModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryModel));
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoryModel>> getAllCategories(@PageableDefault(page = 0, size = 10, sort = "categoryName", direction = Sort.Direction.ASC)Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageable));
	}

}
