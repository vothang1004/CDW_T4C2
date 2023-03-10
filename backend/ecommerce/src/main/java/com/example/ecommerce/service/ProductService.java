package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
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

	public List<Product> searchProductsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return productRepository.findBynameContainingIgnoreCase(keyword);
	}

	public List<Product> getLatestProducts() {
		// Get the latest 10 products
		Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
		return productRepository.findAll(pageable).getContent();
	}

	public List<Product> getMostViewProducts() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("view").descending());
		return productRepository.findAll(pageable).getContent();
	}

	public List<Product> getSuggestedProducts(Long productId) {
		// find the product with the given id
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty()) {
			throw new EntityNotFoundException("Product not found with id: " + productId);
		}
		Product product = optionalProduct.get();

		// get the category of the product
		Category category = product.getCategory();

		// find other products in the same category, ordered by view count in descending
		// order
		Pageable pageable = PageRequest.of(0, 10, Sort.by("view").descending());
		return productRepository.findByCategory(category, pageable).getContent();
	}
	
}
