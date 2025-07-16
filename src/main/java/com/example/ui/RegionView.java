package com.example.ui;

import com.example.service.RegionService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("regions-ui")
public class RegionView extends VerticalLayout {

    public RegionView(@Autowired RegionService regionService) {
        Grid<String> grid = new Grid<>();
        grid.addColumn(region -> region).setHeader("Region Name");
        grid.setItems(regionService.listRegionNames());
        add(grid);
    }
}