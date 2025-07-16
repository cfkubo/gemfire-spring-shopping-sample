package com.example.service;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionService;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SecureRegionService {

    @Autowired
    private ClientCache clientCache;

    public <K, V> Region<K, V> getAuthenticatedRegion(String regionName, String username, String password) {
        Properties userSecurityProps = new Properties();
        userSecurityProps.setProperty("security-username", username);
        userSecurityProps.setProperty("security-password", password);

        // Create an authenticated view for this user
        RegionService authenticatedCache = clientCache.createAuthenticatedView(userSecurityProps);

        // Get the region from the authenticated cache
        return authenticatedCache.getRegion(regionName);
    }
}