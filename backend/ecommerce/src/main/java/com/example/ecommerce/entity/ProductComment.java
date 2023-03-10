package com.example.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_comment")
public class ProductComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "comment", nullable = false, columnDefinition = "TEXT")
	private String comment;

	@Column(name = "create_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createDate;

	@ManyToOne
	@JoinColumn(name = "parent_comment_id")
	private ProductComment parentComment;

	public ProductComment() {
	}

	public ProductComment(User user, Product product, String comment, LocalDateTime createDate,
			ProductComment parentComment) {
		this.user = user;
		this.product = product;
		this.comment = comment;
		this.createDate = createDate;
		this.parentComment = parentComment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public ProductComment getParentComment() {
		return parentComment;
	}

	public void setParentComment(ProductComment parentComment) {
		this.parentComment = parentComment;
	}

	// Getters and setters
}
