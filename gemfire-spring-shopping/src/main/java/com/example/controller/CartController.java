package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.CartService;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    // Get all items in cart
    @GetMapping
    public Collection<Product> getCart() {
        return cartService.getCart().getItems().values();
    }

    // Add product to cart by product ID
    @PostMapping("/add/{prodId}")
    public String addToCart(@PathVariable("prodId") Integer prodId) {
        Product product = productRepository.findById(prodId).orElse(null);
        if (product == null) {
            return "Product not found";
        }
        cartService.addToCart(product);
        return "Added to cart: " + product.getTitle();
    }

    // Remove product from cart by product ID
    @DeleteMapping("/remove/{prodId}")
    public String removeFromCart(@PathVariable Integer prodId) {
        cartService.removeFromCart(prodId);
        return "Removed from cart: " + prodId;
    }

    // Clear cart
    @DeleteMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared";
    }
}