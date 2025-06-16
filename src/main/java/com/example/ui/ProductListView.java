// package com.example.ui;

// import com.example.model.Product;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.notification.Notification;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.spring.annotation.SpringComponent;
// import com.vaadin.flow.spring.annotation.UIScope;
// import org.springframework.web.client.RestTemplate;

// import java.util.Arrays;
// import java.util.List;

// @Route("listproducts")
// @SpringComponent
// @UIScope
// public class ProductListView extends VerticalLayout {

//     private final RestTemplate restTemplate;
//     private final Grid<Product> productGrid = new Grid<>(Product.class);

//     public ProductListView(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//         setupUI();
//     }

//     private void setupUI() {
//         productGrid.addColumn(Product::getProd_id).setHeader("ID");
//         productGrid.addColumn(Product::getTitle).setHeader("Title");
//         productGrid.addColumn(Product::getActor).setHeader("Actor");
//         productGrid.addColumn(Product::getPrice).setHeader("Price");
//         productGrid.addColumn(Product::getCategory).setHeader("Category");
//         productGrid.addColumn(Product::getSpecial).setHeader("Special");
//         productGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");
//         productGrid.setSizeFull();

//         Button loadButton = new Button("Load Products", event -> loadProducts());
//         add(loadButton, productGrid);
//         setSizeFull();
//     }

//     private void loadProducts() {
//         try {
//             List<Product> products = fetchProducts();
//             if (products.isEmpty()) {
//                 Notification.show("No products found in cache.");
//             }
//             productGrid.setItems(products);
//         } catch (Exception e) {
//             Notification.show("Failed to load products: " + e.getMessage());
//         }
//     }

//     private List<Product> fetchProducts() {
//         Product[] productsArray = restTemplate.getForObject("http://localhost:9989/api/listproducts", Product[].class);
//         return Arrays.asList(productsArray != null ? productsArray : new Product[0]);
//     }
// }

// package com.example.ui;

// import com.example.model.Product;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.notification.Notification;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.spring.annotation.SpringComponent;
// import com.vaadin.flow.spring.annotation.UIScope;
// import org.springframework.web.client.RestTemplate;

// import java.util.Arrays;
// import java.util.List;

// @Route("listproducts")
// @SpringComponent
// @UIScope
// public class ProductListView extends VerticalLayout {

//     private final RestTemplate restTemplate;
//     private final Grid<Product> productGrid = new Grid<>(Product.class);

//     public ProductListView(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//         setupUI();
//     }

//     private void setupUI() {
//         productGrid.addColumn(Product::getProd_id).setHeader("ID");
//         productGrid.addColumn(Product::getTitle).setHeader("Title");
//         productGrid.addColumn(Product::getActor).setHeader("Actor");
//         productGrid.addColumn(Product::getPrice).setHeader("Price");
//         productGrid.addColumn(Product::getCategory).setHeader("Category");
//         productGrid.addColumn(Product::getSpecial).setHeader("Special");
//         productGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");
//         productGrid.setSizeFull();

//         Button loadButton = new Button("Load Products", event -> loadProducts());
//         add(loadButton, productGrid);
//         setSizeFull();
//     }

//     private void loadProducts() {
//         try {
//             List<Product> products = fetchProducts();
//             if (products.isEmpty()) {
//                 Notification.show("No products found in cache.");
//             }
//             productGrid.setItems(products);
//         } catch (Exception e) {
//             Notification.show("Failed to load products: " + e.getMessage());
//         }
//     }

//     private List<Product> fetchProducts() {
//         Product[] productsArray = restTemplate.getForObject("http://localhost:9989/api/listproducts", Product[].class);
//         return Arrays.asList(productsArray != null ? productsArray : new Product[0]);
//     }
// }

// package com.example.ui;

// import com.example.model.Product;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.notification.Notification;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import com.vaadin.flow.spring.annotation.SpringComponent;
// import com.vaadin.flow.spring.annotation.UIScope;
// import org.springframework.web.client.RestTemplate;

// import java.util.Arrays;
// import java.util.List;

// @Route("listproducts")
// @SpringComponent
// @UIScope
// public class ProductListView extends VerticalLayout {

//     private final RestTemplate restTemplate;
//     private final Grid<Product> productGrid = new Grid<>(Product.class);

//     public ProductListView(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//         setupUI();
//     }

//     private void setupUI() {
//         productGrid.addColumn(Product::getProd_id).setHeader("ID");
//         productGrid.addColumn(Product::getTitle).setHeader("Title");
//         productGrid.addColumn(Product::getActor).setHeader("Actor");
//         productGrid.addColumn(Product::getPrice).setHeader("Price");
//         productGrid.addColumn(Product::getCategory).setHeader("Category");
//         productGrid.addColumn(Product::getSpecial).setHeader("Special");
//         productGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");
//         productGrid.setSizeFull();

//         Button loadButton = new Button("Load Products", event -> loadProducts());
//         add(loadButton, productGrid);
//         setSizeFull();
//     }

//     private void loadProducts() {
//         try {
//             List<Product> products = fetchProducts();
//             if (products.isEmpty()) {
//                 Notification.show("No products found in cache.");
//             }
//             productGrid.setItems(products);
//         } catch (Exception e) {
//             Notification.show("Failed to load products: " + e.getMessage());
//         }
//     }

//     private List<Product> fetchProducts() {
//         Product[] productsArray = restTemplate.getForObject("http://localhost:9989/api/listproducts", Product[].class);
//         return Arrays.asList(productsArray != null ? productsArray : new Product[0]);
//     }
// }


package com.example.ui;

import com.example.model.Product;
import com.example.service.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Route("listproducts")
@SpringComponent
@UIScope
public class ProductListView extends VerticalLayout {

    private final RestTemplate restTemplate;
    private final CartService cartService;
    private final Grid<Product> productGrid = new Grid<>(Product.class);

    @Autowired
    public ProductListView(RestTemplate restTemplate, CartService cartService) {
        this.restTemplate = restTemplate;
        this.cartService = cartService;
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
        Product[] productsArray = restTemplate.getForObject("http://localhost:9989/api/listproducts", Product[].class);
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