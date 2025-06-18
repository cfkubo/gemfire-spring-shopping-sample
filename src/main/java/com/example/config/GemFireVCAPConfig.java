package com.example.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GemFireVCAPConfig {

    @Bean
    public GemFireLocatorProperties gemFireLocatorProperties(Environment env) {
        String vcapServices = env.getProperty("VCAP_SERVICES");
        String host = "localhost";
        int port = 10334;

        if (vcapServices != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(vcapServices);

                // Adjust the service name below to match your GemFire service label
                JsonNode gemfireNode = root.elements().next().get(0);
                JsonNode credentials = gemfireNode.get("credentials");

                host = credentials.get("locator_host").asText();
                port = credentials.get("locator_port").asInt();
            } catch (Exception e) {
                // Log and fallback to defaults
                System.err.println("Failed to parse VCAP_SERVICES for GemFire: " + e.getMessage());
            }
        }

        return new GemFireLocatorProperties(host, port);
    }

    public static class GemFireLocatorProperties {
        private final String host;
        private final int port;

        public GemFireLocatorProperties(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public String getHost() { return host; }
        public int getPort() { return port; }
    }
}