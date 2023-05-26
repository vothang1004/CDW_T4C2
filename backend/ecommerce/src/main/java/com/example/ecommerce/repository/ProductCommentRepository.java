package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductComment;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
	List<ProductComment> findByProduct(Product product);

	List<ProductComment> findByProductId(Long productId);

	// :parentCommentId IS NULL OR
//	@Query("SELECT p FROM ProductComment p WHERE ( p.parentComment = :parentCommentId) AND (p.product.id = :productId)")
	@Query("SELECT p FROM ProductComment p WHERE (:parentCommentId IS NULL AND (p.parentComment IS NULL AND p.product.id = :productId)) OR (p.parentComment.id = :parentCommentId AND p.product.id = :productId)")
	List<ProductComment> findByProductIdAndParentComment(Long productId, Long parentCommentId);

}
