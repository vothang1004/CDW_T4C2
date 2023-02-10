package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping
	public List<Cart> getAllCarts() {
		return cartService.getAllCarts();
	}

	@GetMapping("/{id}")
	public Cart getCartById(@PathVariable Long id) {
		return cartService.getCartById(id);
	}

	@PostMapping
	public Cart addCart(@RequestBody Cart cart) {
		return cartService.addCart(cart);
	}

	@PutMapping("/{id}")
	public Cart updateCart(@PathVariable Long id, @RequestBody Cart updatedCart) {
		return cartService.updateCart(id, updatedCart);
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable Long id) {
		cartService.deleteCart(id);
	}

	@PostMapping("/{cartId}/products")
	public Cart addProductToCart(@PathVariable Long cartId, @RequestBody Product product) {
		return cartService.addProductToCart(cartId, product);
	}

	@DeleteMapping("/{cartId}/products/{productId}")
	public Cart removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		Product product = new Product();
		product.setId(productId);
		return cartService.removeProductFromCart(cartId, product);
	}
}
