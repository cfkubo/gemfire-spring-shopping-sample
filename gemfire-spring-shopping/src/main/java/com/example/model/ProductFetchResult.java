package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFetchResult {
    private List<Product> products;
    private long durationMillis;
    private String source; // "cache", "database", or "mixed"

    public ProductFetchResult(List<Product> products, long durationMillis, String source) {
        this.products = products;
        this.durationMillis = durationMillis;
        this.source = source;
    }

    public List<Product> getProducts() { return products; }
    public long getDurationMillis() { return durationMillis; }
    public String getSource() { return source; }

    public ProductFetchResult getProducts(int count) {
        long start = System.currentTimeMillis();

        // Step 1: Get the first N product IDs from the DB (but only IDs, not full objects)
        List<Integer> ids = productRepository.findFirstN(count).stream()
            .map(Product::getProd_id)
            .collect(Collectors.toList());

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
        result.addAll(fromCache);
        result.addAll(fromDb);

        long duration = System.currentTimeMillis() - start;
        String source;
        if (fromDb.isEmpty()) source = "cache";
        else if (fromCache.isEmpty()) source = "database";
        else source = "mixed";

        return new ProductFetchResult(result, duration, source);
    }
}