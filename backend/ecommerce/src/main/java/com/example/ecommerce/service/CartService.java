package com.example.ecommerce.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Cart2;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartRepository;

@Service
public class CartService {
	public static final int DEFAULT_AMOUNT = 1;
	@Autowired
	private CartRepository cartRepository;

	public List<Cart2> getAllCarts() {
		return cartRepository.findAll();
	}

	public Cart2 getCartById(Long id) {
		Optional<Cart2> cart = cartRepository.findById(id);
		return cart.orElse(null);
	}

	public Cart2 getCartByUserId(Long userId) {
		Cart2 cart = cartRepository.findByUserId(userId);
		if (cart == null) {
			cart = new Cart2(userId);
			System.out.println("userId: "+userId);
			cartRepository.save(cart);
		}
		return cart;
	}

	public Cart2 addCart(Cart2 cart) {
		return cartRepository.save(cart);
	}

	public Cart2 updateCart(Long id, Cart2 updatedCart) {
		Cart2 cart = getCartById(id);
		if (cart == null) {
			return null;
		}
//    cart.setUser(updatedCart.getUser());
		cart.setUserId(updatedCart.getUserId());
//		cart.setProducts(updatedCart.getProducts());
		cart.setProductsWithAmount(updatedCart.getProductsWithAmount());
		return cartRepository.save(cart);
	}

	public void deleteCart(Long id) {
		cartRepository.deleteById(id);
	}

	public Cart2 addProductToCart(Long cartId, Product product) {
		Cart2 cart = getCartById(cartId);
		if (cart == null) {
			return null;
		}
//    List<Product> products = cart.getProducts();
//		Set<Product> products = cart.getProducts();
//		
//		products.add(product);
		boolean productExists = false;
		Map<Product, Integer> productsWithAmount = cart.getProductsWithAmount();
		for (Product p : cart.getProductsWithAmount().keySet()) {
			if (p.getId().equals(product.getId())) {
				int currentAmount = cart.getProductsWithAmount().get(p);
				cart.getProductsWithAmount().put(p, currentAmount + 1);
				productExists = true;
				break;
			}
		}
		if (!productExists) {
			productsWithAmount.put(product, DEFAULT_AMOUNT);
		}
//		cart.setProducts(products);
		cart.setProductsWithAmount(productsWithAmount);
		return cartRepository.save(cart);
	}

	public Cart2 removeProductFromCart(Long cartId, Product product, int amount) {
		Cart2 cart = getCartById(cartId);
		if (cart == null) {
			return null;
		}
//    List<Product> products = cart.getProducts();
//		Set<Product> products = cart.getProducts();
//		products.remove(product);
//		cart.setProducts(products);
		int currentAmount = 0;
		Product temp = product;
		Map<Product, Integer> productsWithAmount = cart.getProductsWithAmount();
		for (Product p : cart.getProductsWithAmount().keySet()) {
			if (p.getId().equals(product.getId())) {
				temp = p;
				currentAmount = cart.getProductsWithAmount().get(p);
				break;
			}
		}
		int amountafterminus = currentAmount - amount;
		if (amountafterminus <= 0) {
			productsWithAmount.remove(temp);
		} else {
			productsWithAmount.put(temp, amountafterminus);
		}

//		cart.setProducts(products);
		cart.setProductsWithAmount(productsWithAmount);
		return cartRepository.save(cart);
	}
}
