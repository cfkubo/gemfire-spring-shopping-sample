package com.example.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Long orderId;
    private String name;
    private String shippingAddress;
    private String billingAddress;
    private List<Product> products;
    private Double total;

    // Getters and setters

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}