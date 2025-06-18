package com.example.controller;

import com.example.model.Order;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private Region<Long, Order> ordersRegion;

    @GetMapping
    public Collection<Order> getAllOrders() {
        return ordersRegion.values();
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return ordersRegion.get(orderId);
    }
}