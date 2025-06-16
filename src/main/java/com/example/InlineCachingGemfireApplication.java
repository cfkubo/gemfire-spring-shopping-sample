package com.example;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class InlineCachingGemfireApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientCache clientCache;

    @Override
    public void run(String... args) throws Exception {
        // Check PostgreSQL connection
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Successfully connected to PostgreSQL database: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to PostgreSQL: " + e.getMessage());
        }

        // Check GemFire region
        Region<?, ?> region = clientCache.getRegion("dataItemRegion");
        if (region != null) {
            System.out.println("✅ Successfully connected to GemFire region: " + region.getName());
        } else {
            System.err.println("❌ GemFire region 'dataItemRegion' not found!");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(InlineCachingGemfireApplication.class, args);
    }
}