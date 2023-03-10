package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

	User findByEmail(String email);

//	User findbyId(long id);
	User findByResetPasswordToken(String resetPasswordToken);

}
