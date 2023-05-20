package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.model.JwtRequest;
import com.example.ecommerce.model.JwtResponse;
import com.example.ecommerce.service.JwtTokenUtil;
import com.example.ecommerce.service.JwtUserDetailsService;
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/authenticate")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        
        final String token = jwtTokenUtil.generateToken(userDetails);
        final User user = userDetailsService.loadUserByEmail(authenticationRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(token,user));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
        	System.out.println("run me");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
        	System.out.println("run me 2");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
