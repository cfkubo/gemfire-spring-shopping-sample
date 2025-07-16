package com.example.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("vcap")
public class VcapServicesView extends VerticalLayout {

    public VcapServicesView() {
        setSizeFull();
        setPadding(true);

        Label title = new Label("VCAP_SERVICES Info");
        TextArea vcapArea = new TextArea();
        vcapArea.setWidthFull();
        vcapArea.setHeight("400px");
        vcapArea.setReadOnly(true);

        Button refreshBtn = new Button("Refresh", event -> {
            String vcap = System.getenv("VCAP_SERVICES");
            vcapArea.setValue(vcap != null ? vcap : "VCAP_SERVICES not set.");
        });

        add(title, vcapArea, refreshBtn);

        // Load on view open
        refreshBtn.click();
    }
}