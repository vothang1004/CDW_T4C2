package com.example.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//java persistence or org.springframework???
//import org.hibernate.annotations.Entity;
//import org.hibernate.annotations.Table;
//import org.springframework.data.annotation.Id;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal tax = BigDecimal.ZERO;

	@Column(name = "create_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/*
	 * UPDATE CURRENT_TIMESTAMP : auto adjust updateDate when this row is updated.
	 */
	@Column(name = "update_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	public OrderDetail() {
		// TODO Auto-generated constructor stub
	}

	public OrderDetail(Long id, Order order, Product product, Integer quantity, BigDecimal price, BigDecimal discount,
			BigDecimal tax, Date createDate, Date updateDate) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
		this.tax = tax;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	// Constructors, getters and setters

}
