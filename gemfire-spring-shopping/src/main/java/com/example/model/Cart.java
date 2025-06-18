package com.example.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Integer, Product> items = new LinkedHashMap<>();

    public void addProduct(Product product) {
        items.put(product.getProd_id(), product);
    }

    public void removeProduct(Integer prodId) {
        items.remove(prodId);
    }

    public Map<Integer, Product> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}