package com.example.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Cart2;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.JwtTokenUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private JwtTokenUtil tokenService;

	@GetMapping
	public List<Cart2> getAllCarts() {
		return cartService.getAllCarts();
	}

	@GetMapping("/listmycard")
	public Cart2 listMyCard(HttpServletRequest request) {
		return getCartfromrequest(request);
	}

	private Cart2 getCartfromrequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		long userId = tokenService.getUserIdFromBearToken(token);
		Cart2 cart = cartService.getCartByUserId(userId);
		
		return cart;
	}

	@GetMapping("/{id}")
	public Cart2 getCartById(@PathVariable Long id) {
		return cartService.getCartById(id);
	}

//	@PostMapping(consumes = {"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Cart2 addCart(@RequestBody Cart2 cart) {
		if (cart == null || cart.getUserId() == null || cart.getProductsWithAmount() == null) {
			System.out.println("st wrong");
			// throw new ("Missing required parameters");
		}
		return cartService.addCart(cart);
	}

	@PutMapping("/{id}")
	public Cart2 updateCart(@PathVariable Long id, @RequestBody Cart2 updatedCart) {
		return cartService.updateCart(id, updatedCart);
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable Long id) {
		cartService.deleteCart(id);
	}

	@PostMapping("/products")
	public Cart2 addProductToCart(HttpServletRequest request, @RequestBody Product product) {
		Cart2 cart = getCartfromrequest(request);
		return cartService.addProductToCart(cart.getId(), product);
	}

	@DeleteMapping("/products/{productId}/{amount}")
	public Cart2 removeProductFromCart(HttpServletRequest request, @PathVariable Long productId,
			@PathVariable Integer amount) {
		Cart2 cart = getCartfromrequest(request);
		Product product = new Product();
		product.setId(productId);
		return cartService.removeProductFromCart(cart.getId(), product, amount);
	}
}
