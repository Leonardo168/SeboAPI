package com.Leonardo168.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Leonardo168.api.models.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID>{

	boolean existsByCategoryName(String categoryName);

	Optional<CategoryModel> findByCategoryName(String categoryName);
}
