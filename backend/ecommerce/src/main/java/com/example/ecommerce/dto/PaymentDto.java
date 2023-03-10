package com.example.ecommerce.dto;

import java.math.BigDecimal;

public class PaymentDto {

	private Long orderId;
	private BigDecimal amount;
	private String currency;
	private String paymentMethod;
	private String status;

	public PaymentDto() {
		// TODO Auto-generated constructor stub
	}

	public PaymentDto(Long orderId, BigDecimal amount, String currency, String paymentMethod, String status) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
