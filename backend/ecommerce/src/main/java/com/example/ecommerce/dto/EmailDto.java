package com.example.ecommerce.dto;

public class EmailDto {
	private String email;

	public EmailDto(String email) {
		super();
		this.email = email;
	}
	public EmailDto() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "EmailDto [email=" + email + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
