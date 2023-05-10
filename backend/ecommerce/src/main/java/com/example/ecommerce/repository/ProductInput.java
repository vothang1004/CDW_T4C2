package com.example.ecommerce.repository;

import org.springframework.data.domain.PageRequest;

public class ProductInput {
	boolean isBestSelling;
	PageRequest of;
	String category;
	String name;
	public ProductInput(boolean isBestSelling, PageRequest of, String category, String name) {
		super();
		this.isBestSelling = isBestSelling;
		this.of = of;
		this.category = category;
		this.name = name;
	}
	public ProductInput() {
		// TODO Auto-generated constructor stub
	}
	public boolean isBestSelling() {
		return isBestSelling;
	}
	public void setBestSelling(boolean isBestSelling) {
		this.isBestSelling = isBestSelling;
	}
	public PageRequest getOf() {
		return of;
	}
	public void setOf(PageRequest of) {
		this.of = of;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
