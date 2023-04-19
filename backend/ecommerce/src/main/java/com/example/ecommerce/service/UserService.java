package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.UserRegistrationException;
import com.example.ecommerce.model.UserNewPassword;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private CartRepository cartRepository;
	@Autowired
	private HttpServletRequest request;

	public boolean isEmailExists(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	public User getUserByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
	
	public String getBaseUrl() {

		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + "://" + serverName + ":" + serverPort + contextPath + "/";
	}

	public String forgotpassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new RuntimeException("Invalid email address");
		}
		String token = UUID.randomUUID().toString();
		user.setResetPasswordToken(token);
		userRepository.save(user);
		String resetPasswordLink ="refresh code:" + token;

		try {
			String subject = "reset password chuyendeweb project";
			String content = resetPasswordLink;
			MailService.sendMail(user.getEmail(), subject, content);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send reset password link");
		}

		return "Reset password link sent to email";
	}

	public User registerUser(User user) throws UserRegistrationException {
		if (isEmailExists(user.getEmail())) {
			throw new UserRegistrationException("Email already exists");
		}
		// encode user's password using BCryptPasswordEncoder
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(long id) {
		Optional<User> user = userRepository.findById((int) id);
//		Optional<User> user = null;
		return user.orElse(null);
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Long id, User updatedUser) {
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
		return userRepository.save(user);
	}

	@Transactional
	public void deleteUser(Integer userId) {
		try {
			// Delete cart of that user - one user contains only one cart

//			Cart2 cart = cartRepository.findByUserId(userId);
//			cartRepository.delete(cart);

//		        // Delete all orders
//		        List<Order> orders = orderRepository.findByUserId(userId);
//		        for (Order order : orders) {
//		            orderRepository.delete(order);
//		        }

			// Delete the user
			userRepository.deleteById(userId);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting user and its related data", e);
		}
	}

	public void changePassword(long userid, UserNewPassword newPassword) {
		// TODO Auto-generated method stub
		User user = getUserById(userid);
		if (user == null) {
			return;
		}
		String newPasswordEncode = new BCryptPasswordEncoder().encode(newPassword.getNewPassword());
		user.setPassword(newPasswordEncode);
		userRepository.save(user);
	}
}
