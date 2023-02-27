package com.example.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.model.Cart2;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.JwtTokenUtil;

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
	    // Get token from request header
		System.out.println("run here? 1");
	    String token = request.getHeader("Authorization");
	    System.out.println("run here? 2");
	    if (token == null || !token.startsWith("Bearer ")) {
//	        throw new UnauthorizedException("Missing or invalid authorization header");
	    }
	    System.out.println("run here? 3");
	    token = token.substring(7);
	    
	    // Extract user ID from token
	    long userId = tokenService.getUserIdFromToken(token);
	    System.out.println(userId);
//	    System.out.println("run here? 4");
	    // Retrieve cards for user
//	    List<Cart> carts = cartService.getCartById(Long.parseLong(userId));
	    Cart2 carts = cartService.getCartByUserId(userId);
	    System.out.println(carts);
//	    System.out.println("cart: "+carts);
	    return carts;
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

	@PostMapping("/{cartId}/products")
	public Cart2 addProductToCart(@PathVariable Long cartId, @RequestBody Product product) {
		return cartService.addProductToCart(cartId, product);
	}

	@DeleteMapping("/{cartId}/products/{productId}")
	public Cart2 removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		Product product = new Product();
		product.setId(productId);
		return cartService.removeProductFromCart(cartId, product);
	}
}
