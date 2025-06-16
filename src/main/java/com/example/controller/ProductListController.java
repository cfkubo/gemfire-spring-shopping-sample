// Source code is decompiled from a .class file using FernFlower decompiler.
package com.example.controller;

import com.example.model.Product;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.QueryService;
import org.apache.geode.cache.query.SelectResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductListController {

    private final Region<Integer, Product> productsRegion;

    @Autowired
    private ClientCache clientCache;

    public ProductListController(Region<Integer, Product> productsRegion) {
        this.productsRegion = productsRegion;
    }

    @GetMapping("/listproducts")
    public List<Product> listProducts() throws Exception {
        QueryService queryService = clientCache.getQueryService();
        SelectResults<Product> results = (SelectResults<Product>) queryService
            .newQuery("SELECT * FROM /productsRegion")
            .execute();
        System.out.println("Queried region size: " + results.size());
        return results.stream().collect(Collectors.toList());
    }
}
