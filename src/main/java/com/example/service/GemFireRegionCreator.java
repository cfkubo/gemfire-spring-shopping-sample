// package com.example.service;

// import org.apache.geode.management.api.ClusterManagementService;
// import org.apache.geode.management.client.ClusterManagementServiceBuilder;
// import org.apache.geode.management.configuration.RegionConfig;
// import org.apache.geode.management.configuration.RegionType;
// import org.apache.geode.management.api.ClusterManagementResult; // Make sure this is imported

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import javax.annotation.PostConstruct;

// @Component
// public class GemFireRegionCreator {

//     @Value("${spring.data.gemfire.management.url}")
//     private String managementUrl;

//     @Value("${spring.data.gemfire.security.username}")
//     private String username;

//     @Value("${spring.data.gemfire.security.password}")
//     private String password;

//     @Value("${app.gemfire.region-name:productsRegion}") // Re-use from application.properties
//     private String productsRegionName; // Use the value set for productsRegionName

//     private static final String DATA_ITEM_REGION_NAME = "dataItemRegion";

//     @PostConstruct
//     public void createRegionsIfNecessary() {
//         System.out.println("GemFireRegionCreator: Attempting to create regions if necessary...");
//         System.out.println("GemFireRegionCreator: Connecting to management URL: " + managementUrl);

//         try {
//             // Build ClusterManagementService client
//             ClusterManagementService cms = ClusterManagementServiceBuilder.build(managementUrl)
//                     .setUsername(username)
//                     .setPassword(password)
//                     .build();

//             // 1. Create 'productsRegion'
//             createRegion(cms, productsRegionName, RegionType.PARTITION);

//             // 2. Create 'dataItemRegion'
//             createRegion(cms, DATA_ITEM_REGION_NAME, RegionType.PARTITION);

//         } catch (Exception e) {
//             System.err.println("GemFireRegionCreator: ERROR - Failed to connect to Cluster Management Service or create regions: " + e.getMessage());
//             e.printStackTrace();
//             // Consider re-throwing or failing fast if regions are critical
//         }
//     }

//     private void createRegion(ClusterManagementService cms, String regionName, RegionType regionType) {
//         System.out.println("GemFireRegionCreator: Checking and creating region: " + regionName + " with type " + regionType.name());

//         try {
//             RegionConfig regionConfig = new RegionConfig();
//             regionConfig.setName(regionName);
//             regionConfig.setType(regionType);

//             ClusterManagementResult<RegionConfig> result = cms.create(regionConfig);

//             // FIX STARTS HERE
//             if (result.isSuccessful()) { // This line was incomplete before
//                 System.out.println("GemFireRegionCreator: Successfully created region: " + regionName);
//             } else { // This 'else' now has a corresponding 'if'
//                 String errorMessage = "GemFireRegionCreator: Failed to create region " + regionName + ": " + result.getStatusCode() + " - " + result.getStatusMessage();
//                 if (result.getMemberStatuses() != null) {
//                     result.getMemberStatuses().forEach(ms -> System.err.println("  Member Status: " + ms.getMemberName() + " -> " + ms.getMessage()));
//                 }
//                 // Check for "already exists" message in status message
//                 if (result.getStatusMessage() != null && (result.getStatusMessage().contains("already exists") || result.getStatusMessage().contains("already created"))) {
//                     System.out.println("GemFireRegionCreator: Region " + regionName + " already exists. Skipping creation.");
//                 } else {
//                     System.err.println(errorMessage);
//                 }
//             }
//             // FIX ENDS HERE

//         } catch (Exception e) {
//             System.err.println("GemFireRegionCreator: Exception when creating region " + regionName + ": " + e.getMessage());
//             e.printStackTrace();
//             // If the region exists, CMS will often throw an exception. We catch it.
//             // A more specific exception check would be ideal if using a particular Geode version's API
//             if (e.getMessage() != null && (e.getMessage().contains("already exists") || e.getMessage().contains("already created"))) {
//                 System.out.println("GemFireRegionCreator: Region " + regionName + " already exists. Skipping creation due to exception.");
//             }
//         }
//     }
// }