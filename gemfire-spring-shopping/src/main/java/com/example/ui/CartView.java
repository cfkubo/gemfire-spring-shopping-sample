package com.example.ui;

import com.example.model.Product;
import com.example.service.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("cart")
public class CartView extends VerticalLayout {

    private final CartService cartService;
    private final Grid<Product> cartGrid = new Grid<>(Product.class);

    @Autowired
    public CartView(CartService cartService) {
        this.cartService = cartService;

        cartGrid.addColumn(Product::getProd_id).setHeader("ID");
        cartGrid.addColumn(Product::getTitle).setHeader("Title");
        cartGrid.addColumn(Product::getActor).setHeader("Actor");
        cartGrid.addColumn(Product::getPrice).setHeader("Price");
        cartGrid.addColumn(Product::getCategory).setHeader("Category");
        cartGrid.addColumn(Product::getSpecial).setHeader("Special");
        cartGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");

        // Remove from cart button
        cartGrid.addComponentColumn(product -> {
            Button removeBtn = new Button("Remove", event -> {
                cartService.removeFromCart(product.getProd_id());
                Notification.show(product.getTitle() + " removed from cart");
                refreshCart();
            });
            return removeBtn;
        }).setHeader("Actions");

        Button clearCartBtn = new Button("Clear Cart", event -> {
            cartService.clearCart();
            Notification.show("Cart cleared");
            refreshCart();
        });

        Button checkoutBtn = new Button("Go to Checkout", event -> getUI().ifPresent(ui -> ui.navigate("checkout")));

        add(clearCartBtn, checkoutBtn, cartGrid);
        setSizeFull();

        refreshCart();
    }

    private void refreshCart() {
        cartGrid.setItems(cartService.getCart().getItems().values());
    }
}