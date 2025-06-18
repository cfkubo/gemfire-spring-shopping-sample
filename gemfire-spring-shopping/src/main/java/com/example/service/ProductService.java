package com.example.service;

import com.example.model.Product;
import com.example.model.ProductFetchResult;
import com.example.repository.ProductRepository;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private Region<Integer, Product> productsRegion;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataSource dataSource;

    public ProductFetchResult getProducts(int count) {
        long start = System.currentTimeMillis();

        // Step 1: Get the first N product IDs from the DB (only IDs, not full objects)
        List<Integer> ids = productRepository.findFirstNIds(count);

        List<Product> fromCache = new ArrayList<>();
        List<Integer> missingIds = new ArrayList<>();

        // Step 2: Check cache for each ID
        for (Integer id : ids) {
            Product cached = productsRegion.get(id);
            if (cached != null) {
                fromCache.add(cached);
            } else {
                missingIds.add(id);
            }
        }

        List<Product> fromDb = new ArrayList<>();
        if (!missingIds.isEmpty()) {
            // Step 3: Query DB only for missing IDs
            fromDb = productRepository.findAllById(missingIds);
            for (Product p : fromDb) {
                productsRegion.put(p.getProd_id(), p);
            }
        }

        List<Product> result = new ArrayList<>();
        // Preserve order
        for (Integer id : ids) {
            Product p = productsRegion.get(id);
            if (p != null) result.add(p);
        }

        long duration = System.currentTimeMillis() - start;
        String source;
        if (fromDb.isEmpty()) source = "cache";
        else if (fromCache.isEmpty()) source = "database";
        else source = "mixed";

        return new ProductFetchResult(result, duration, source);
    }

    public String getDbStatus() {
        try (Connection conn = dataSource.getConnection()) {
            return "Connected";
        } catch (Exception e) {
            return "Not Connected";
        }
    }

    public String getGemfireStatus() {
        Region<?, ?> region = clientCache.getRegion("productsRegion");
        return (region != null) ? "Connected" : "Not Connected";
    }
}