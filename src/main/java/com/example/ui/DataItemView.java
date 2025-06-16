package com.example.ui;

// Ensure this import matches the actual location of DataItemResponse
import com.example.model.DataItemResponse;
import com.example.service.DataService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class DataItemView extends VerticalLayout {

    private final DataService dataService;
    private final Grid<DataItemResponse> grid = new Grid<>(DataItemResponse.class, false);

    @Autowired
    public DataItemView(DataService dataService) {
        this.dataService = dataService;

        TextField idField = new TextField("DataItem ID");
        Button fetchButton = new Button("Fetch Data", event -> fetchData(idField.getValue()));

        grid.addColumn(resp -> resp.getDataItem() != null ? resp.getDataItem().getId() : "")
                .setHeader("ID");
        grid.addColumn(resp -> resp.getDataItem() != null ? resp.getDataItem().getName() : "")
                .setHeader("Name");
        grid.addColumn(resp -> resp.getDataItem() != null ? resp.getDataItem().getDescription() : "")
                .setHeader("Description");
        grid.addColumn(DataItemResponse::getSource).setHeader("Source");
        grid.addColumn(DataItemResponse::getDurationMillis).setHeader("Time (ms)");

        add(idField, fetchButton, grid);
    }

    private void fetchData(String id) {
        try {
            DataItemResponse response = dataService.getDataByIdWithTiming(id);
            if (response.getDataItem() == null) {
                Notification.show("No data found for ID " + id);
            } else {
                grid.setItems(response);
            }
        } catch (Exception e) {
            Notification.show("Error: " + e.getMessage());
        }
    }
}