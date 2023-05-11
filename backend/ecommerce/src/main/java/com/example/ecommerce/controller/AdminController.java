package com.example.ecommerce.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.UserRegistrationException;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.SaleService;
import com.example.ecommerce.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SaleService saleService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/product")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}

	@PutMapping("/product/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}

	@DeleteMapping("/product/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		user.setCreateDate(new Date());
		if (userService.isEmailExists(user.getEmail())) {
			throw new UserRegistrationException("Email already exists");
		}
		// encode user's password using BCryptPasswordEncoder
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userService.addUser(user);
	}

	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
		return updateUserAdmin(id, updatedUser);
	}

	public User updateUserAdmin(Long id, User updatedUser) {
//		System.out.println(id);
		User user = getUserById(id);
		if (user == null) {
			return null;
		}
		// username be able to change
		user.setUsername(updatedUser.getUsername());
//		user.setPassword(updatedUser.getPassword());
//		user.setEmail(updatedUser.getEmail());
		user.setPhoneNumber(updatedUser.getPhoneNumber());
		user.setDob(updatedUser.getDob());
		user.setGender(updatedUser.getGender());
		user.setProfile(updatedUser.getProfile());
		user.setActive(updatedUser.getActive());
		user.setUserRole(updatedUser.getUserRole());

		return userRepository.save(user);
	}

	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/revenue")
	public ResponseEntity<?> getRevenue(
			@RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        double revenue = orderService.calculateRevenue(startDate, endDate);

		BigDecimal revenue = orderService.calculateRevenue(startDate.atStartOfDay(), endDate.atStartOfDay());
		return ResponseEntity.ok(revenue);
	}
//	@GetMapping("/revenue")
//	public ResponseEntity<?> getRevenue(@RequestParam("start_date") String startDateStr,
//			@RequestParam("end_date") String endDateStr) {
//		LocalDate startDate = LocalDate.parse(startDateStr);
//		LocalDate endDate = LocalDate.parse(endDateStr);
//		BigDecimal revenue = orderService.calculateRevenue(startDate, endDate);
//		return ResponseEntity.ok(revenue);
//	}

//	@GetMapping("/revenue")
//	public ResponseEntity<?> getRevenue(@RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//	                                     @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//	    BigDecimal revenue = orderService.calculateRevenue(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
//	    return ResponseEntity.ok(revenue);
//	}
	@GetMapping("/sales/category/{categoryId}/month/{month}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getSalesByCategoryAndMonth(@PathVariable("categoryId") Long categoryId,
			@PathVariable("month") Month month) {
		BigDecimal saleDtos = saleService.getSalesByCategoryAndMonth(categoryId, month);
		return ResponseEntity.ok(saleDtos);
	}
}