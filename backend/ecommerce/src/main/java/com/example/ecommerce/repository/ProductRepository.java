package com.example.ecommerce.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<Product> findBynameContainingIgnoreCase(String keyword);

	List<Product> findByCategory(Category category);

	Page<Product> findByCategory(Category category, Pageable pageable);
	//because all data get into is only several, so paging from there is only there.!
	@Query("SELECT p FROM Product p WHERE (lower(p.name) LIKE %:keyword%) AND (:category IS NULL OR p.category = :category) AND (:isBestSelling IS NULL OR p.isBestSelling = :isBestSelling)")
	List<Product> finds(String keyword, Category category, Boolean isBestSelling);

//findByNameContainingIgnoreCaseOrCategory
	Page<Product> findAll(Pageable pageable);

//	Page<Product> findByIsBestSelling(boolean isBestSelling, PageRequest of);
//	Page<Product> findByIsBestSelling(boolean isBestSelling, Pageable of);
//
//	@Query("SELECT p FROM Product p WHERE p.isBestSelling = :isBestSelling AND p.category = :category AND p.name = :name")
//	Page<Product> findBestSellingProducts(boolean isBestSelling, Pageable pageable, Category category, String name);

//	@Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category) AND (:name IS NULL OR p.name LIKE %:name%) AND (:isBestSelling = false OR p.bestSelling = true)")
//	Page<Product> findByCategoryAndNameAndIsBestSelling(@Param("isBestSelling") boolean isBestSelling, Pageable pageable,
//			@Param("category") Category category, @Param("name") String name);

	@Query("SELECT p FROM Product p WHERE (lower(p.name) LIKE %:keyword%) AND (:category IS NULL OR p.category = :category) AND (:isBestSelling IS NULL OR p.isBestSelling = :isBestSelling)")
	Page<Product> findByCategoryAndNameAndIsBestSelling(String keyword, Category category, Boolean isBestSelling, Pageable pageable);

	@Modifying
	@Transactional //everyupdate or delete must be a transaction
	@Query("UPDATE Product p SET p.view = p.view + 1 WHERE p.id = :productId")
	void incrementViewCount(@Param("productId") Long productId);
}
