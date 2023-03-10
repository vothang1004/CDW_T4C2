package com.example.ecommerce.dto;

import java.time.LocalDateTime;

public class ProductReviewDto {
	private Long id;
	private String username;
	private Integer rating;
	private LocalDateTime createDate;
	private Long productId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductReviewDto(Long id, String username, Integer rating, LocalDateTime createDate, Long productId) {
		super();
		this.id = id;
		this.username = username;
		this.rating = rating;
		this.createDate = createDate;
		this.productId = productId;
	}

	public ProductReviewDto() {
		// TODO Auto-generated constructor stub
	}
	// getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
}
