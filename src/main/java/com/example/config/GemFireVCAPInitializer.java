package com.example.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GemFireVCAPInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        String vcap = System.getenv("VCAP_SERVICES");
        if (vcap == null) return;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(vcap);

            // Find the GemFire service (adjust the label as needed)
            Iterator<String> fieldNames = root.fieldNames();
            while (fieldNames.hasNext()) {
                String serviceLabel = fieldNames.next();
                if (serviceLabel.contains("gemfire")) {
                    JsonNode service = root.get(serviceLabel).get(0);
                    JsonNode credentials = service.get("credentials");
                    String host = credentials.get("locator_host").asText();
                    int port = credentials.get("locator_port").asInt();

                    Map<String, Object> props = new HashMap<>();
                    props.put("spring.data.gemfire.locator.host", host);
                    props.put("spring.data.gemfire.locator.port", port);

                    ConfigurableEnvironment env = ctx.getEnvironment();
                    env.getPropertySources().addFirst(new MapPropertySource("vcap-gemfire", props));
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse VCAP_SERVICES for GemFire: " + e.getMessage());
        }
    }
}