// package com.example.ui;

// import com.example.model.Product;
// import com.example.service.CartService;
// import com.example.service.CheckoutService;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.grid.Grid;
// import com.vaadin.flow.component.notification.Notification;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import org.springframework.beans.factory.annotation.Autowired;

// @Route("checkout")
// public class CheckoutView extends VerticalLayout {

//     private final CartService cartService;
//     private final CheckoutService checkoutService;
//     private final Grid<Product> cartGrid = new Grid<>(Product.class);

//     @Autowired
//     public CheckoutView(CartService cartService, CheckoutService checkoutService) {
//         this.cartService = cartService;
//         this.checkoutService = checkoutService;

//         cartGrid.addColumn(Product::getProd_id).setHeader("ID");
//         cartGrid.addColumn(Product::getTitle).setHeader("Title");
//         cartGrid.addColumn(Product::getActor).setHeader("Actor");
//         cartGrid.addColumn(Product::getPrice).setHeader("Price");
//         cartGrid.addColumn(Product::getCategory).setHeader("Category");
//         cartGrid.addColumn(Product::getSpecial).setHeader("Special");
//         cartGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");

//         cartGrid.setItems(cartService.getCart().getItems().values());

//         Button checkoutBtn = new Button("Checkout", event -> {
//             String result = checkoutService.checkout();
//             Notification.show(result, 5000, Notification.Position.MIDDLE);
//             cartGrid.setItems(cartService.getCart().getItems().values());
//         });

//         add(cartGrid, checkoutBtn);
//         setSizeFull();
//     }
// }

package com.example.ui;

import com.example.model.Product;
import com.example.service.CartService;
import com.example.service.CheckoutService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("checkout")
public class CheckoutView extends VerticalLayout {

    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final Grid<Product> cartGrid = new Grid<>(Product.class);

    @Autowired
    public CheckoutView(CartService cartService, CheckoutService checkoutService) {
        this.cartService = cartService;
        this.checkoutService = checkoutService;

        cartGrid.addColumn(Product::getProd_id).setHeader("ID");
        cartGrid.addColumn(Product::getTitle).setHeader("Title");
        cartGrid.addColumn(Product::getActor).setHeader("Actor");
        cartGrid.addColumn(Product::getPrice).setHeader("Price");
        cartGrid.addColumn(Product::getCategory).setHeader("Category");
        cartGrid.addColumn(Product::getSpecial).setHeader("Special");
        cartGrid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");

        cartGrid.setItems(cartService.getCart().getItems().values());

        TextField nameField = new TextField("Name");
        TextField shippingField = new TextField("Shipping Address");
        TextField billingField = new TextField("Billing Address");

        Button checkoutBtn = new Button("Checkout", event -> {
            String result = checkoutService.checkout(
                nameField.getValue(),
                shippingField.getValue(),
                billingField.getValue()
            );
            Notification.show(result, 5000, Notification.Position.MIDDLE);
            cartGrid.setItems(cartService.getCart().getItems().values());
        });

        add(nameField, shippingField, billingField, cartGrid, checkoutBtn);
        setSizeFull();
    }
}