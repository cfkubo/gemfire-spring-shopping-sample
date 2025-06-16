package com.example.controller;

import com.example.model.Order;
import com.example.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    // Checkout with user info (POST for safety)
    @PostMapping
    public String checkout(
            @RequestParam("name") String name,
            @RequestParam("shippingAddress") String shippingAddress,
            @RequestParam("billingAddress") String billingAddress
    ) {
        return checkoutService.checkout(name, shippingAddress, billingAddress);
    }
}