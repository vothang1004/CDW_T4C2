package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.model.JwtUserDetails;
import com.example.ecommerce.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        System.out.println("runme");
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		System.out.println("user database: " + user.getUserRole());

//      System.out.println("user: "+user.toString());
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("id", user.getId());
		userDetails.put("username", user.getUsername());
		userDetails.put("email", user.getEmail());
//      String java = "hello";
//      org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(user.getUsername(), encodedPassword,
//              new ArrayList<>());
		org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), new ArrayList<>());
//      System.out.println("user password from database : "+u.getPassword());
//        return u;
		// You can add additional user details here
		//get authority
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole().name().toUpperCase()));
		JwtUserDetails userDetails1 = new JwtUserDetails(user.getId(), user.getEmail(), user.getEmail(),
				user.getPassword(), authorities);
		System.out.println("User authorities: " + userDetails1.getAuthorities());

		return userDetails1;
	}
//	UserDetails admin = org.springframework.security.core.userdetails.User.builder()
//    .username("admin")
//    .password("{noop}password")
//    .roles("ADMIN").authorities(authorities)
//    .build();
	public User loadUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

}
