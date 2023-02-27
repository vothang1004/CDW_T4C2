package com.example.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

@Entity
@Table(name = "variant_option")
public class VariantOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;
    
    @NotBlank
    private String name;
    
    @Min(0)
    private Integer sortOrder;
    
    // constructors
    
    public VariantOption() {}
    
    public VariantOption(ProductVariant productVariant, String name, Integer sortOrder) {
        this.productVariant = productVariant;
        this.name = name;
        this.sortOrder = sortOrder;
    }
    
    // getters and setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "VariantOption{" +
                "id=" + id +
                ", productVariant=" + productVariant +
                ", name='" + name + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
