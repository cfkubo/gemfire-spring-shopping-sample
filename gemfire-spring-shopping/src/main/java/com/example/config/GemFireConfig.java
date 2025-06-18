// package com.example.config;

// import com.example.model.DataItem;
// import org.apache.geode.cache.client.ClientCache;
// import org.apache.geode.cache.client.ClientCacheFactory;
// import org.apache.geode.cache.client.ClientRegionFactory;
// import org.apache.geode.cache.client.ClientRegionShortcut;
// import org.apache.geode.cache.Region;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;


// @Configuration
// public class GemFireConfig {

//     @Bean
//     public ClientCache clientCache() {
//         return new ClientCacheFactory()
//                 .setPoolSubscriptionEnabled(true)
//                 .create();
//     }

//     @Bean
//     public Region<String, DataItem> dataItemRegion(ClientCache clientCache) {
//         ClientRegionFactory<String, DataItem> regionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
//         return regionFactory.create("dataItemRegion");
//     }
// }

package com.example.config;

import com.example.model.DataItem;
import com.example.model.Order;
import com.example.model.Product;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GemFireConfig {

    @Value("${app.gemfire.region-name}")
    private String regionName;

    @Value("${spring.data.gemfire.locator.host:localhost}")
    private String locatorHost;

    @Value("${spring.data.gemfire.locator.port:10334}")
    private int locatorPort;

    @Bean
    public Region<Long, Order> ordersRegion(ClientCache clientCache) {
        ClientRegionFactory<Long, Order> regionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
        return regionFactory.create("ordersRegion");
    }

    @Bean
    public ClientCache clientCache() {
        return new ClientCacheFactory()
                .setPoolSubscriptionEnabled(true)
                .addPoolLocator(locatorHost, locatorPort)
                .create();
    }

    @Bean
    public Region<Integer, Product> productsRegion(ClientCache clientCache) {
        ClientRegionFactory<Integer, Product> regionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
        return regionFactory.create(regionName);
    }

    @Bean
    public Region<String, DataItem> dataItemRegion(ClientCache clientCache) {
        ClientRegionFactory<String, DataItem> regionFactory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
        return regionFactory.create("dataItemRegion");
    }
}