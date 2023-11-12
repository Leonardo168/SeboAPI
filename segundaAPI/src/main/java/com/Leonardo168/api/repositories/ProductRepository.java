package com.Leonardo168.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Leonardo168.api.models.CategoryModel;
import com.Leonardo168.api.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID>{

	Optional<ProductModel> findByIsbn(String isbn);

	Optional<Page<ProductModel>> findByCategory(CategoryModel categoryModel, Pageable pageable);

	Optional<Page<Object>> findByIsbn(String isbn, Pageable pageable);

}
