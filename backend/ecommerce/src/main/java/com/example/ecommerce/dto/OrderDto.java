package com.example.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

//import org.springframework.security.core.userdetails.User;

//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

//import org.apache.tomcat.jni.User;

//import org.apache.catalina.User;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;

public class OrderDto {

	private Long id;
//	private Integer userId;
	private User user;
	private LocalDateTime orderDate;
	private String shippingAddress;
	private String billingAddress;
	private BigDecimal totalPrice;
	private String trackingNumber;
	private String paymentMethod;
	private String orderStatus;
	private String notes;
	private List<OrderDetailDto> orderDetailDtos;

	public OrderDto() {
	}

	public List<OrderDetailDto> getOrderDetailDtos() {
		return orderDetailDtos;
	}

	public void setOrderDetailDtos(List<OrderDetailDto> orderDetailDtos) {
		this.orderDetailDtos = orderDetailDtos;
	}

	

	public OrderDto(Long id, User user, LocalDateTime orderDate, String shippingAddress, String billingAddress,
			BigDecimal totalPrice, String trackingNumber, String paymentMethod, String orderStatus, String notes,
			List<OrderDetailDto> orderDetailDtos) {
		super();
		this.id = id;
		this.user = user;
		this.orderDate = orderDate;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.totalPrice = totalPrice;
		this.trackingNumber = trackingNumber;
		this.paymentMethod = paymentMethod;
		this.orderStatus = orderStatus;
		this.notes = notes;
		this.orderDetailDtos = orderDetailDtos;
	}

	public OrderDto(Order order) {
		this.id = order.getId();
		this.user = order.getUser();
		this.orderDate = order.getOrderDate();
		this.shippingAddress = order.getShippingAddress();
		this.billingAddress = order.getBillingAddress();
		this.totalPrice = order.getTotalPrice();
		this.trackingNumber = order.getTrackingNumber();
		this.paymentMethod = order.getPaymentMethod();
		this.orderStatus = order.getOrderStatus();
		this.notes = order.getNotes();
	}

	

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
