package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.UserRegistrationException;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	public boolean isEmailExists(String email) {
	    User user = userRepository.findByEmail(email);
	    return user != null;
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
		System.out.println("find all");
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
//		Optional<User> user = userRepository.findById(id);
		Optional<User> user = null;
		return user.orElse(null);
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(Long id, User updatedUser) {
		User user = getUserById(id);
		if (user == null) {
			return null;
		}
		user.setUsername(updatedUser.getUsername());
		user.setPassword(updatedUser.getPassword());
		user.setEmail(updatedUser.getEmail());
		user.setPhoneNumber(updatedUser.getPhoneNumber());
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
}
