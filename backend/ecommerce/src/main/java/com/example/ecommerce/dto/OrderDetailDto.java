package com.example.ecommerce.dto;

import java.math.BigDecimal;

import com.example.ecommerce.entity.Product;

public class OrderDetailDto {

    private Long id;
    private Long orderId;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal tax;

    public OrderDetailDto() {
    }

    

    public OrderDetailDto(Long id, Long orderId, Product product, Integer quantity, BigDecimal price,
			BigDecimal discount, BigDecimal tax) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.tax = tax;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

   

    public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
