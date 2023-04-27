package com.example.ecommerce.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.SaleService;

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

	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return ResponseEntity.ok(categories);
	}

    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue(@RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
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