package com.example.ecommerce.model;

import java.io.Serializable;

import com.example.ecommerce.entity.User;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;
    private final User user;
    

    public JwtResponse(String jwttoken, User user) {
		super();
		this.jwttoken = jwttoken;
		this.user = user;
	}


	public String getToken() {
        return this.jwttoken;
    }
	public User getUser() {
		return this.user;
	}
}
