package com.example.ecommerce.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.ecommerce.dto.EmailDto;
import com.example.ecommerce.dto.ResetPasswordRequest;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enumeration.UserRole;
import com.example.ecommerce.exception.UserRegistrationException;
import com.example.ecommerce.model.UserNewPassword;
import com.example.ecommerce.service.JwtTokenUtil;
import com.example.ecommerce.service.UserService;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil tokenService;

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

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestBody EmailDto emaildto) {
		String response = userService.forgotpassword(emaildto.getEmail());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot-password-mono")
	public Mono<ResponseEntity<String>> forgotPasswordMono(@RequestBody EmailDto emailDto) {
		return userService.forgotpasswordMono(emailDto.getEmail()).map(response -> ResponseEntity.ok(response));
	}

	@GetMapping("/reset-password")
	public ResponseEntity<?> showResetPasswordForm(@RequestParam("token") String token) {
		User user = userService.getUserByResetPasswordToken(token);

		if (user == null) {
			return ResponseEntity.badRequest().body("Invalid reset password token");
		}

		// return reset password form view
		return ResponseEntity.ok("Reset password form view - depricated from 16/04/2023");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		User user = userService.getUserByResetPasswordToken(resetPasswordRequest.getToken());

		if (user == null) {
			return ResponseEntity.badRequest().body("Invalid reset password token");
		}

		// update user password
		user.setPassword(resetPasswordRequest.getPassword());
		user.setResetPasswordToken(null);
//		userService.saveUser(user);
		UserNewPassword p = new UserNewPassword(resetPasswordRequest.getPassword());
		userService.changePassword(user.getId(), p);
		// return success message
		return ResponseEntity.ok("Password reset successful");
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

	@PutMapping
	public User updateUser(HttpServletRequest request, @RequestBody User updatedUser) {
		return userService.updateUser(getIdUserByRequest(request), updatedUser);
	}

	public long getIdUserByRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		long userId = tokenService.getUserIdFromBearToken(token);
		return userId;
	}

	@PutMapping("/changepassword")
	public ResponseEntity<String> changePassword(HttpServletRequest request, @RequestBody UserNewPassword newPassword) {
		long userid = getIdUserByRequest(request);
		userService.changePassword(userid, newPassword);
		return new ResponseEntity<String>("change successfully", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}
}
