package com.example.ecommerce.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enumeration.UserRole;
import com.example.ecommerce.exception.UserRegistrationException;
import com.example.ecommerce.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
//	{
//	    "username": "john_doe",
//	    "email": "john.doe@example.com",
//	    "password": "password123",
//	    "phoneNumber": "123-456-7890",
//	    "dob": "1990-01-01",
//	    "gender": "male"
//}
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		System.out.println("run me");
		try {
			Date now = new Date();
	        user.setCreateDate(now);
	        user.setLastLoginDate(null);
	        user.setUserRole(UserRole.user);
	        user.setActive(true);
	        userService.registerUser(user);
	        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	    } catch (MethodArgumentTypeMismatchException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	@ExceptionHandler(UserRegistrationException.class)
	public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}
}
