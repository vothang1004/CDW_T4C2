package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.ProductReview;
import com.example.ecommerce.repository.ProductReviewRepository;

@Service
public class ProductReviewService {

	@Autowired
	private ProductReviewRepository productReviewRepository;

	public ProductReview addProductReview(ProductReview productReview) {
		return productReviewRepository.save(productReview);
	}

	public List<ProductReview> findByProductId(Long productId) {
		// TODO Auto-generated method stub
		return productReviewRepository.findByProductId(productId);
	}
}
