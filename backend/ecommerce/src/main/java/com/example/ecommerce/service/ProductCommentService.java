package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ProductCommentDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductComment;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.repository.ProductCommentRepository;

@Service
public class ProductCommentService {
	@Autowired
	private ProductCommentRepository productCommentRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;

	public ProductCommentService(ProductCommentRepository productCommentRepository, ProductService productService,
			UserService userService) {
		this.productCommentRepository = productCommentRepository;
		this.productService = productService;
		this.userService = userService;
	}

	public ProductCommentService() {
		// TODO Auto-generated constructor stub
	}

	public ProductComment addProductComment(Long productId, ProductCommentDto productCommentDto, User user) {
		Product product = productService.getProductById(productId);

		ProductComment parentComment = null;
		if (productCommentDto.getParentCommentId() != null) {
			parentComment = productCommentRepository.findById(productCommentDto.getParentCommentId())
					.orElseThrow(() -> new ResourceNotFoundException("Parent comment", "id",
							productCommentDto.getParentCommentId()));
		}

		ProductComment productComment = new ProductComment();
		productComment.setUser(user);
		productComment.setProduct(product);
		productComment.setComment(productCommentDto.getComment());
		productComment.setParentComment(parentComment);
		productComment.setCreateDate(LocalDateTime.now()); // Set the createDate field to the current date and time

		return productCommentRepository.save(productComment);
	}

	public ProductCommentDto replyProductComment(Long productId, Long commentId, ProductCommentDto productCommentDto,
			User user) {
		// Find the parent comment
		ProductComment parentComment = getProductCommentById(commentId);
		if (parentComment == null) {
			new ResourceNotFoundException("Parent comment", "id", productCommentDto.getParentCommentId());
		}
		// Create the new child comment
		ProductComment childComment = new ProductComment();
		childComment.setProduct(parentComment.getProduct());
		childComment.setUser(user);
		childComment.setComment(productCommentDto.getComment());
		childComment.setParentComment(parentComment);
		childComment.setCreateDate(LocalDateTime.now()); // Set the createDate field to the current date and time

		// Save the child comment
		productCommentRepository.save(childComment);
		// Convert the saved comment to a DTO and return it in the response
		// productCommentMapper
		ProductCommentDto savedCommentDto = mapToDto(childComment);
		return savedCommentDto;
	}

	public List<ProductComment> getProductComments(Long productId) {
		Product product = productService.getProductById(productId);
		return productCommentRepository.findByProduct(product);
	}

	public List<ProductCommentDto> getCommentsForProduct(Long productId) {
		List<ProductComment> comments = productCommentRepository.findByProductId(productId);
		List<ProductCommentDto> commentDtos = new ArrayList<>();
		for (ProductComment comment : comments) {
			commentDtos.add(mapToDto(comment));
		}
		return commentDtos;
	}

	public ProductComment getProductCommentById(Long commentId) {
		ProductComment comment = productCommentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		return comment;
	}

	private ProductCommentDto mapToDto(ProductComment comment) {
		ProductCommentDto dto = new ProductCommentDto();
		dto.setId(comment.getId());
		dto.setComment(comment.getComment());
		dto.setCreateDate(comment.getCreateDate());
		dto.setUser(comment.getUser().getUsername());
		dto.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
		return dto;
	}

	public List<ProductCommentDto> getCommentsForProductAndParentId(Long productId, Long parentCommentId) {
		List<ProductComment> comments = productCommentRepository.findByProductIdAndParentComment(productId,parentCommentId);
		List<ProductCommentDto> commentDtos = new ArrayList<>();
		for (ProductComment comment : comments) {
			commentDtos.add(mapToDto(comment));
		}
		return commentDtos;
	}
}
