package com.example.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ProductCommentDto;
import com.example.ecommerce.dto.ProductReviewDto;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.ProductReview;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.JwtTokenUtil;
import com.example.ecommerce.service.ProductCommentService;
import com.example.ecommerce.service.ProductReviewService;
import com.example.ecommerce.service.ProductService;
import com.sun.jdi.InvalidCodeIndexException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private JwtTokenUtil tokenService;
	@Autowired
	private ProductReviewService productReviewService;
	@Autowired
	private ProductCommentService productCommentService;

//	@GetMapping
//	public List<Product> getAllProducts() {
//		return productService.getAllProducts();
//	}

	@GetMapping
	public Page<Product> showProductsPage(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(defaultValue = "none") String isBestSelling,
			@RequestParam(defaultValue = "none") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir,
			@RequestParam(defaultValue = "") String search, 
			@RequestParam(defaultValue = "-1") int categoryId) {
		Page<Product> productPage;
		Category c = null;
		if (categoryId > -1) {
			c = new Category();
			c.setId(categoryId);
		}
		page -= 1;
		if (page < 0) {
			return null;
		}
		Boolean isBest = null;
		if (!isBestSelling.equals("none"))
			isBest = Boolean.valueOf(isBestSelling);
		productPage = productService.productPage(search, c, isBest, page, limit, sortBy, sortDir);
		return productPage;
	}

	@GetMapping("/test")
	public List<Product> m(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "-1") int categoryId,
			@RequestParam(defaultValue = "none") String isBestSelling) {

		Category c = null;
		if (categoryId > -1) {
			c = new Category();
			c.setId(categoryId);
		}
		Boolean isBest = null;
		if (!isBestSelling.equals("none"))
			isBest = Boolean.valueOf(isBestSelling);
		System.out.println("isbest: " + isBest);
		return productService.productPage(search, c, isBest);
	}

	// usable
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable("id") Long id) {
		productService.incrementViewCount(id);
		return productService.getProductById(id);
	}

	// usable
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProductsByKeyword(@RequestParam String keyword) {
		List<Product> products = productService.searchProductsByKeyword(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/latest")
	public ResponseEntity<List<Product>> getLatestProducts() {
		List<Product> latestProducts = productService.getLatestProducts();
		return new ResponseEntity<>(latestProducts, HttpStatus.OK);
	}

	@GetMapping("/most-viewed-products")
	public ResponseEntity<List<Product>> getMostViewedProducts() {
		// Get the 10 most viewed products
		return new ResponseEntity<>(productService.getMostViewProducts(), HttpStatus.OK);
	}

	@GetMapping("/suggested-products/{product_id}")
	public ResponseEntity<List<Product>> getSuggestedProducts(@PathVariable("product_id") Long productId) {
		List<Product> suggestedProducts = productService.getSuggestedProducts(productId);
		return new ResponseEntity<>(suggestedProducts, HttpStatus.OK);
	}

	@GetMapping("/{productId}/ratings")
	public ResponseEntity<List<ProductReviewDto>> getProductRatings(@PathVariable Long productId) {
		List<ProductReviewDto> productReviews = productReviewService.findByProductId(productId).stream()
				.map(this::EntityToDto).collect(Collectors.toList());
		return ResponseEntity.ok(productReviews);
	}

	public ProductReviewDto EntityToDto(ProductReview productReview) {
		ProductReviewDto productReviewDto = new ProductReviewDto();
		productReviewDto.setId(productReview.getId());
		productReviewDto.setUsername(productReview.getUser().getUsername());
		productReviewDto.setProductId(productReview.getProduct().getId());
		productReviewDto.setRating(productReview.getRating());
		productReviewDto.setCreateDate(productReview.getCreateDate());
		return productReviewDto;
	}

	@PostMapping("/{productId}/ratings")
	public ResponseEntity<String> addProductRating(HttpServletRequest request, @PathVariable Long productId,
			@RequestBody Map<String, Integer> requestBody) {
		Integer rating = requestBody.get("rating");
		if (rating < 0 || rating > 5) {
			return new ResponseEntity<String>("must one to five rating", HttpStatus.BAD_REQUEST);
		}
		Product product = productService.getProductById(productId);

		if (product == null) {
			return ResponseEntity.notFound().build();
		}

		User currentUser = tokenService.getUserByRequest(request);
		ProductReview productReview = new ProductReview();
		productReview.setProduct(product);
		productReview.setUser(currentUser);
		productReview.setRating(rating);
		productReview.setCreateDate(LocalDateTime.now());
		productReview.setUpdateDate(LocalDateTime.now());

		productReviewService.addProductReview(productReview);

		return ResponseEntity.ok("Rating added successfully.");
	}

	@PostMapping("/{productId}/comments")
	public ResponseEntity<?> addComment(HttpServletRequest request, @PathVariable Long productId,
			@RequestBody ProductCommentDto productComment) {
//		productComment.setId(productId);
		User user = tokenService.getUserByRequest(request);
		productCommentService.addProductComment(productId, productComment, user);
		return new ResponseEntity<>("Comment added successfully.", HttpStatus.CREATED);
	}

	@PostMapping("/{productId}/comments/{commentId}/reply")
	public ResponseEntity<ProductCommentDto> replyToComment(HttpServletRequest request, @PathVariable Long productId,
			@PathVariable Long commentId, @RequestBody ProductCommentDto productCommentDto) {

//	    // Find the parent comment
//	    ProductComment parentComment = productCommentService.getProductCommentById(commentId);
//	    if (parentComment == null) {
//	        return ResponseEntity.notFound().build();
//	    }
		User user = tokenService.getUserByRequest(request);
//	    // Create the new child comment
//	    ProductComment childComment = new ProductComment();
//	    childComment.setProduct(parentComment.getProduct());
//	    childComment.setUser(user);
//	    childComment.setComment(productCommentDto.getComment());
//	    childComment.setParentComment(parentComment);
//	    childComment.setCreateDate(LocalDateTime.now()); // Set the createDate field to the current date and time
//
//	    // Save the child comment
//	    ProductComment savedComment = productService.createProductComment(childComment);
//
//	    // Convert the saved comment to a DTO and return it in the response
//	    ProductCommentDto savedCommentDto = productCommentMapper.toProductCommentDto(savedComment);
		ProductCommentDto savedCommentDto = productCommentService.replyProductComment(productId, commentId,
				productCommentDto, user);
		return ResponseEntity.ok(savedCommentDto);
	}

	@GetMapping("/{productId}/comments")
	public ResponseEntity<List<ProductCommentDto>> getProductComments(@PathVariable Long productId) {
		List<ProductCommentDto> comments = productCommentService.getCommentsForProduct(productId);
		return ResponseEntity.ok(comments);
	}

	// admin

	// rating 1 edition
//	@PostMapping("/{productId}/ratings")
//	public ResponseEntity<Rating> addRating(@PathVariable Long productId, @RequestBody RatingRequest ratingRequest) {
//		Product product = productService.getProductById(productId);
//		if (product == null) {
//			return ResponseEntity.notFound().build();
//		}
//
//		Rating rating = new Rating();
//		rating.setProduct(product);
//		rating.setRating(ratingRequest.getRating());
//		rating = ratingService.saveRating(rating);
//
//		return ResponseEntity.ok(rating);
//	}
}
