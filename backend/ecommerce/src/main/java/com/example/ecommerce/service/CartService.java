package com.example.ecommerce.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;

@Service
public class CartService {
	public static final int DEFAULT_AMOUNT = 1;
	@Autowired
	private CartRepository cartRepository;

	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	public Cart getCartById(Long id) {
		Optional<Cart> cart = cartRepository.findById(id);
		return cart.orElse(null);
	}

	public Cart addCart(Cart cart) {
		return cartRepository.save(cart);
	}

	public Cart updateCart(Long id, Cart updatedCart) {
		Cart cart = getCartById(id);
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

	public Cart addProductToCart(Long cartId, Product product) {
		Cart cart = getCartById(cartId);
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

	public Cart removeProductFromCart(Long cartId, Product product) {
		Cart cart = getCartById(cartId);
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
		int amountafterminus = currentAmount - 1;
		if (amountafterminus <= 0) {
			productsWithAmount.remove(temp);
		}else {
			productsWithAmount.put(temp, amountafterminus);
		}
		
//		cart.setProducts(products);
		cart.setProductsWithAmount(productsWithAmount);
		return cartRepository.save(cart);
	}
}
