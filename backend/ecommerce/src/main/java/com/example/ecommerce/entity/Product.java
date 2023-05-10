package com.example.ecommerce.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	@NotBlank
	private String name;

	private String description;

	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal price;

	@Min(value = 0)
	private Integer productStock;

	private String linkImage;

	@Min(value = 0)
	private Integer view;
	
	@DecimalMin(value = "0.0")
	private BigDecimal sale;

	@CreationTimestamp
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@UpdateTimestamp
	@Column(name = "update_date")
	private LocalDateTime updateDate;
	@Column(name = "is_best_selling", nullable = false, columnDefinition = "BIT(1) default 0")
	private boolean isBestSelling;

	public Product() {
	}

	public Product(Category category, String name, String description, BigDecimal price, Integer productStock,
			String linkImage, Integer view, BigDecimal sale) {
		this.category = category;
		this.name = name;
		this.description = description;
		this.price = price;
		this.productStock = productStock;
		this.linkImage = linkImage;
		this.view = view;
		this.sale = sale;
	}

	// getters and setters

	public Product(Long productId) {
		// TODO Auto-generated constructor stub
		id = productId;
	}

	public Product(Product product) {
		// TODO Auto-generated constructor stub
		this.id = product.id;
		this.category = product.category;
		this.name = product.name;
		this.description = product.description;
		this.price = product.price;
		this.productStock = product.productStock;
		this.linkImage = product.linkImage;
		this.view = product.view;
		this.sale = product.sale;
		this.createDate = product.createDate;
		this.updateDate = product.updateDate;
	}

	public boolean isBestSelling() {
		return isBestSelling;
	}

	public void setBestSelling(boolean isBestSelling) {
		this.isBestSelling = isBestSelling;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public BigDecimal getSale() {
		return sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = sale;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", productStock=" + productStock + ", linkImage=" + linkImage + ", view=" + view
				+ ", sale=" + sale + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}

}
