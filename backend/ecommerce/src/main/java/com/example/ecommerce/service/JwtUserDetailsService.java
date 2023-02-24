package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.JwtUserDetails;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
      System.out.println("user: "+user.toString());
      String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
      Map<String, Object> userDetails = new HashMap<>();
      userDetails.put("id", user.getId());
      userDetails.put("username", user.getUsername());
      userDetails.put("email", user.getEmail());
//      String java = "hello";
      org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(user.getUsername(), encodedPassword,
              new ArrayList<>());
      System.out.println("user: "+u.getPassword());
//        return u;
      // You can add additional user details here
      JwtUserDetails userDetails1 = new JwtUserDetails(user.getId(), user.getUsername(), user.getEmail(),
    		  encodedPassword, new ArrayList<>());
      return userDetails1;
    }
}