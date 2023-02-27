package com.example.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.springframework.data.annotation.Id;

@Entity
//@Table(name = "`order`")

@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "notes")
    private String notes;

    // constructors

    public Order() {
    }

    public Order(User user, String shippingAddress, String billingAddress, BigDecimal totalPrice,
            String paymentMethod, String orderStatus) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
    }

    // getters and setters

   

    public User getUser() {
        return user;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    // toString

    @Override
    public String toString() {
        return "Order [id=" + id + ", user=" + user + ", orderDate=" + orderDate + ", shippingAddress="
                + shippingAddress + ", billingAddress=" + billingAddress + ", totalPrice=" + totalPrice
                + ", trackingNumber=" + trackingNumber + ", paymentMethod=" + paymentMethod + ", orderStatus="
                + orderStatus + ", notes=" + notes + "]";
    }

}
