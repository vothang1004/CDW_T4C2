package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public void incrementViewCount(long productId) {
		productRepository.incrementViewCount(productId);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(int id) {
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent() || !product.isEmpty()) {

		}
		return product.orElse(null);
	}

	public Product addProduct(Product product) {
		product.setCategory(categoryRepository.findById(product.getCategory().getId()).get());
		product.setCreateDate(LocalDateTime.now());
		return productRepository.save(product);
	}

	public Product updateProduct(int id, Product updatedProduct) {
		Product product = getProductById(id);
		if (product == null) {
			return null;
		}
		product.setName(updatedProduct.getName());
		product.setCategory(categoryRepository.findById(updatedProduct.getCategory().getId()).get());
		product.setProductStock(updatedProduct.getProductStock());
		product.setLinkImage(updatedProduct.getLinkImage());
		product.setView(updatedProduct.getView());
		product.setSale(updatedProduct.getSale());
		product.setDescription(updatedProduct.getDescription());
		product.setPrice(updatedProduct.getPrice());
		product.setBestSelling(updatedProduct.isBestSelling());
		product.setUpdateDate(LocalDateTime.now());
		return productRepository.save(product);
	}

	public void deleteProduct(int id) {
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

	public List<Product> getSuggestedProducts(int productId) {
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

//	public Page<Product> productPage(int page, int max) {
//		Page<Product> productPage = productRepository.findAll(PageRequest.of(page, max));
//		return productPage;
//	}

	public Page<Product> productPage(String search, Category category, Boolean isBestSelling, int page, int max,
			String sortBy, String sortDirection) {
//		Category category = categoryRepository.findById(categoryId).orElse(null);
		Pageable pageable = PageRequest.of(page, max);
		if (!sortBy.equalsIgnoreCase("none")) {
			Sort.Direction direction; // Sort direction
			if ("asc".equalsIgnoreCase(sortDirection)) {
				direction = Sort.Direction.ASC;
			} else if ("desc".equalsIgnoreCase(sortDirection)) {
				direction = Sort.Direction.DESC;
			} else {
				direction = null; // default, no sorting
			}
			pageable = PageRequest.of(page, max, direction, sortBy);
		}
//		if (sortBy.equalsIgnoreCase("none")) {
//			Page<Product> productPage = productRepository.findBestSellingProducts(isBestSelling,
//					PageRequest.of(page, max), category, search);
//			return productPage;
//		}
//		System.out.println("SEARCH: " + search);
		// PageRequest.of(page, max,Sort.by(sortBy))
		// category,
		Page<Product> productPage = productRepository.findByCategoryAndNameAndIsBestSelling(search, category,
				isBestSelling, pageable);
		return productPage;
	}

	public List<Product> productPage(String search, Category category, Boolean isBestSelling) {
//		if(
		List<Product> productPage = productRepository.finds(search, category, isBestSelling);

		return productPage;
	}
}
