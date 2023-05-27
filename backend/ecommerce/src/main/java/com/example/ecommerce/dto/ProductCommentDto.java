package com.example.ecommerce.dto;

import java.time.LocalDateTime;

public class ProductCommentDto {
	private Long id;
	private String comment;
	private LocalDateTime createDate;
	private String user;
	private Long parentCommentId;
	private Long sizeChild;
	public ProductCommentDto() {
		// TODO Auto-generated constructor stub
	}

	public ProductCommentDto(String comment, Long parentCommentId) {
		super();
		this.comment = comment;
		this.parentCommentId = parentCommentId;
	}

	

	public ProductCommentDto(Long id, String comment, LocalDateTime createDate, String user, Long parentCommentId,
			Long sizeChild) {
		super();
		this.id = id;
		this.comment = comment;
		this.createDate = createDate;
		this.user = user;
		this.parentCommentId = parentCommentId;
		this.sizeChild = sizeChild;
	}

	public Long getSizeChild() {
		return sizeChild;
	}

	public void setSizeChild(Long sizeChild) {
		this.sizeChild = sizeChild;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	// getters and setters
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getParentCommentId() {
		return parentCommentId;
	}

	public void setParentCommentId(Long parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

}
