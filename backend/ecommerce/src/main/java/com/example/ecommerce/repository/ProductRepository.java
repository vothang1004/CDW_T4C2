package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findBynameContainingIgnoreCase(String keyword);

	Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

	Page<Product> findByIsBestSelling(boolean isBestSelling, PageRequest of);
	Page<Product> findByIsBestSelling(boolean isBestSelling, Pageable of);


}
