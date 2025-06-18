// package com.example.service;

// import com.example.model.Product;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.Collection;

// @Service
// public class CheckoutService {

//     @Autowired
//     private CartService cartService;

//     public String checkout() {
//         Collection<Product> items = cartService.getCart().getItems().values();
//         if (items.isEmpty()) {
//             return "Your cart is empty!";
//         }
//         double total = items.stream()
//                 .mapToDouble(p -> p.getPrice() != null ? p.getPrice().doubleValue() : 0.0)
//                 .sum();
//         int count = items.size();
//         cartService.clearCart();
//         return "Checkout successful! " + count + " items purchased. Total: $" + String.format("%.2f", total);
//     }
// }

package com.example.service;

import com.example.model.Order;
import com.example.model.Product;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    @Autowired
    private CartService cartService;

    @Autowired
    private Region<Long, Order> ordersRegion;

    private final AtomicLong orderIdGenerator = new AtomicLong(System.currentTimeMillis());

    public String checkout(String name, String shippingAddress, String billingAddress) {
        Collection<Product> items = cartService.getCart().getItems().values();
        if (items.isEmpty()) {
            return "Your cart is empty!";
        }
        double total = items.stream()
                .mapToDouble(p -> p.getPrice() != null ? p.getPrice().doubleValue() : 0.0)
                .sum();

        Order order = new Order();
        order.setOrderId(orderIdGenerator.incrementAndGet());
        order.setName(name);
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setProducts(items.stream().collect(Collectors.toList()));
        order.setTotal(total);

        ordersRegion.put(order.getOrderId(), order);
        cartService.clearCart();
        return "Checkout successful! Order #" + order.getOrderId() + " placed. Total: $" + String.format("%.2f", total);
    }
}