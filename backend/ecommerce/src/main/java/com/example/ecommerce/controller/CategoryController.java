package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping()
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return ResponseEntity.ok(categories);
	}
}
