package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
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
	public void deleteUser(Long userId) {
		try {
			// Delete cart of that user - one user contains only one cart

			Cart cart = cartRepository.findByUserId(userId);
			cartRepository.delete(cart);

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
