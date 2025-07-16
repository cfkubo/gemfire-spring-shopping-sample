package com.example.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service.GfshService;

@Route("gfsh")
public class GfshView extends VerticalLayout {

    public GfshView(@Autowired GfshService gfshService) {
        TextArea commandInput = new TextArea("GFSH Command");
        commandInput.setWidthFull();
        commandInput.setHeight("100px");

        Button runButton = new Button("Run Command");
        TextArea outputArea = new TextArea("Output");
        outputArea.setWidthFull();
        outputArea.setHeight("300px");
        outputArea.setReadOnly(true);

        runButton.addClickListener(e -> {
            String command = commandInput.getValue();
            try {
                String output = gfshService.runCommand(command);
                outputArea.setValue(output);
            } catch (Exception ex) {
                outputArea.setValue("Error: " + ex.getMessage());
                Notification.show("Failed to run command", 3000, Notification.Position.MIDDLE);
            }
        });

        add(commandInput, runButton, outputArea);
        setWidth("800px");
        setMargin(true);
    }
}

