package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

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

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
