package com.example.ecommerce.repository;


import com.example.ecommerce.model.Cart2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart2, Long> {
	Cart2 findByUserId(Long userId);
}




