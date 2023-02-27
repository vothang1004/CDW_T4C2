package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    return product.orElse(null);
  }

  public Product addProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product updatedProduct) {
    Product product = getProductById(id);
    if (product == null) {
      return null;
    }
    product.setName(updatedProduct.getName());
    product.setDescription(updatedProduct.getDescription());
    product.setPrice(updatedProduct.getPrice());
    return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
