package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

	List<ProductReview> findByProductId(int productId);
    // Add any additional custom query methods here
}
