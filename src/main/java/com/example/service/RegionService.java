package com.example.service;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegionService {

    @Autowired
    private GemFireCache gemFireCache;

    public Set<String> listRegionNames() {
        Set<Region<?, ?>> regions = gemFireCache.rootRegions();
        Set<String> regionNames = new java.util.HashSet<>();
        for (Region<?, ?> region : regions) {
            regionNames.add(region.getName());
        }
        return regionNames;
    }
}