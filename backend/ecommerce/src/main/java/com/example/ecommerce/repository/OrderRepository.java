package com.example.ecommerce.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUser(User user);

	List<Order> findByUserId(Long userId);

	List<Order> findByUserId(Integer id);

	List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.category.id = :categoryId AND MONTH(o.orderDate) = :month AND o.paymentMethod != 'not payment yet'")
	BigDecimal findTotalAmountByCategoryIdAndMonthAndPayment(@Param("categoryId") Long categoryId,
			@Param("month") int month);

}
