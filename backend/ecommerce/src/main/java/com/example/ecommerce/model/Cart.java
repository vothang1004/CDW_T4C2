package com.example.ecommerce.model;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import com.example.ecommerce.serializer.CartSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "carts")
@JsonSerialize(using = CartSerializer.class)
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Column(name = "user_id")
	private Long userId;
//	@OneToOne
//	@JoinColumn(name = "user_id")
//	private User user;

//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "cart_id")
//	@ManyToMany
//	@JoinTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"),
//	inverseJoinColumns = @JoinColumn(name = "product_id"))
//	private Set<Product> products;
	@ElementCollection
	@CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"))
	@MapKeyJoinColumn(name = "product_id")
	@Column(name = "amount")
	private Map<Product, Integer> productsWithAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<Product, Integer> getProductsWithAmount() {
		return productsWithAmount;
	}

	public void setProductsWithAmount(Map<Product, Integer> productsWithAmount) {
		this.productsWithAmount = productsWithAmount;
	}

	@Override
	public String toString() {
		return "Map<Product, Integer> products = " + productsWithAmount.entrySet().stream()
				.map(entry -> entry.getKey().getName() + " : " + entry.getValue()).collect(Collectors.toList());
	}
//	public Set<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(Set<Product> products) {
//		this.products = products;
//	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}

}
