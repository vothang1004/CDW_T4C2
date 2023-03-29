package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductComment;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
	List<ProductComment> findByProduct(Product product);

	List<ProductComment> findByProductId(Long productId);

}
