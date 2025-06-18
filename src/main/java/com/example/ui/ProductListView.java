package com.example.ui;

import com.example.model.Product;
import com.example.service.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest; // Import for getting current request
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties; // Import ServerProperties
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest; // Use jakarta.servlet for Spring Boot 3+

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Route("listproducts")
@SpringComponent
@UIScope
public class ProductListView extends VerticalLayout {

    private final RestTemplate restTemplate;
    private final CartService cartService;
    private final Grid<Product> productGrid = new Grid<>(Product.class);
    private final ServerProperties serverProperties; // Inject ServerProperties

    @Autowired
    public ProductListView(RestTemplate restTemplate, CartService cartService, ServerProperties serverProperties) {
        this.restTemplate = restTemplate;
        this.cartService = cartService;
        this.serverProperties = serverProperties; // Assign injected ServerProperties
        setupUI();
    }

    private void setupUI() {
        productGrid.addColumn(Product::getProd_id).setHeader("ID");
        productGrid.addColumn(Product::getTitle).setHeader("Title");
        productGrid.addColumn(Product::getActor).setHeader("Actor");
        productGrid.addColumn(Product::getPrice).setHeader("Price");
        productGrid.addColumn(Product::getCategory).setHeader("Category");
        productGrid.addColumn(Product::getSpecial).setHeader("Special");
        productGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");

        // Add "Add to Cart" button column
        productGrid.addComponentColumn(product -> {
            Button addButton = new Button("Add to Cart", event -> {
                cartService.addToCart(product);
                Notification.show(product.getTitle() + " added to cart!");
            });
            return addButton;
        }).setHeader("Cart");

        productGrid.setSizeFull();

        Button viewCartButton = new Button("View Cart", event -> showCart());
        Button goToCartBtn = new Button("Go to Cart", event -> getUI().ifPresent(ui -> ui.navigate("cart")));
        HorizontalLayout topBar = new HorizontalLayout(viewCartButton, goToCartBtn);
        add(topBar, productGrid);
        setSizeFull();

        loadProducts();
    }

    private void loadProducts() {
        try {
            List<Product> products = fetchProducts();
            if (products.isEmpty()) {
                Notification.show("No products found in cache.");
            }
            productGrid.setItems(products);
        } catch (Exception e) {
            Notification.show("Failed to load products: " + e.getMessage());
        }
    }

    private List<Product> fetchProducts() {
        // Get the current HttpServletRequest
        HttpServletRequest request = Optional.ofNullable(VaadinServletRequest.getCurrent())
                                            .map(VaadinServletRequest::getHttpServletRequest)
                                            .orElseThrow(() -> new IllegalStateException("No active HttpServletRequest"));

        // Get the server port, using 8080 as a fallback if not configured
        int port = serverProperties.getPort() != null ? serverProperties.getPort() : 8080;

        // Construct the base URL dynamically
        String baseUrl = String.format("%s://%s:%d",
                request.getScheme(),
                request.getServerName(),
                port);

        // Append the API path
        String apiUrl = baseUrl + "/api/listproducts";

        Product[] productsArray = restTemplate.getForObject(apiUrl, Product[].class);
        return Arrays.asList(productsArray != null ? productsArray : new Product[0]);
    }

    private void showCart() {
        StringBuilder sb = new StringBuilder("Cart:\n");
        cartService.getCart().getItems().values().forEach(product ->
            sb.append(product.getTitle()).append(" (ID: ").append(product.getProd_id()).append(")\n")
        );
        Notification.show(sb.toString(), 5000, Notification.Position.MIDDLE);
    }
}
