package com.example.service;

import com.example.model.Cart;
import com.example.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CartService {
    private final Cart cart = new Cart();

    public void addToCart(Product product) {
        cart.addProduct(product);
    }

    public void removeFromCart(Integer prodId) {
        cart.removeProduct(prodId);
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}