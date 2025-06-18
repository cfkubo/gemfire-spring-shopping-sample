package com.example.ui;

import com.example.model.Product;
import com.example.model.ProductFetchResult;
import com.example.service.ProductService;
import com.example.service.StatusService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("products")
public class ProductView extends VerticalLayout {

    private final ProductService productService;
    private final Grid<Product> grid = new Grid<>(Product.class, false);
    private final TextField timingField = new TextField("Query Time (ms)");
    private final TextField sourceField = new TextField("Source");
    private final TextField dbStatusField = new TextField("Database Status");
    private final TextField cacheStatusField = new TextField("Cache Status");

    @Autowired
    public ProductView(ProductService productService, StatusService statusService) {
        this.productService = productService;

        dbStatusField.setReadOnly(true);
        cacheStatusField.setReadOnly(true);
        timingField.setReadOnly(true);
        sourceField.setReadOnly(true);

        dbStatusField.setValue(statusService.getDbStatus());
        cacheStatusField.setValue(statusService.getGemfireStatus());

        NumberField countField = new NumberField("Number of Products");
        countField.setValue(10d);

        Button fetchButton = new Button("Fetch Products", event -> fetchProducts(countField.getValue().intValue()));
        fetchButton.getStyle().set("background-color", "#1976d2"); // Material blue
        fetchButton.getStyle().set("color", "white");
        fetchButton.getStyle().set("font-weight", "bold");

        HorizontalLayout topRow = new HorizontalLayout(countField, fetchButton);
        topRow.setAlignItems(Alignment.END);

        // // Place all controls/status fields in a single row
        // HorizontalLayout controls = new HorizontalLayout(
        //     topRow, timingField, sourceField, dbStatusField, cacheStatusField
        // );
        // controls.setWidthFull();
        // controls.setAlignItems(Alignment.END);

        // add(controls, grid);


        grid.addColumn(Product::getProd_id).setHeader("ID");
        grid.addColumn(Product::getTitle).setHeader("Title");
        grid.addColumn(Product::getActor).setHeader("Actor");
        grid.addColumn(Product::getPrice).setHeader("Price");
        grid.addColumn(Product::getCategory).setHeader("Category");
        grid.addColumn(Product::getSpecial).setHeader("Special");
        grid.addColumn(Product::getCommon_prod_id).setHeader("Common Prod ID");

        // Place all controls/status fields in a single row
        HorizontalLayout controls = new HorizontalLayout(
            topRow, countField, fetchButton, timingField, sourceField, dbStatusField, cacheStatusField
        );

        controls.setWidthFull();
        controls.setAlignItems(Alignment.END);

        add(controls, grid);
    }

    private void fetchProducts(int count) {
        try {
            ProductFetchResult result = productService.getProducts(count);
            if (result.getProducts().isEmpty()) {
                Notification.show("No products found.");
            } else {
                grid.setItems(result.getProducts());
                timingField.setValue(String.valueOf(result.getDurationMillis()));
                sourceField.setValue(result.getSource());
            }
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}