package com.example.service;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class StatusService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientCache clientCache;

    @Value("${spring.datasource.url}")
    private String dbType;

    @Value("${app.cache.type:Unknown}")
    private String cacheType;

    public String getDbStatus() {
        try (Connection conn = dataSource.getConnection()) {
            return dbType + " Connected";
        } catch (Exception e) {
            return dbType + " Not Connected";
        }
    }

    public String getGemfireStatus() {
        Region<?, ?> region = clientCache.getRegion("productsRegion");
        return cacheType + (region != null ? " Connected" : " Not Connected");
    }
}