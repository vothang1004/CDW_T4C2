package com.example.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.Cart2;

public interface CartRepository extends JpaRepository<Cart2, Long> {
	Cart2 findByUserId(Long userId);
}




