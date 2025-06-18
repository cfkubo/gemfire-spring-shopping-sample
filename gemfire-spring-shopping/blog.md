---
title: Turbocharge Your Spring Boot App: Inline Caching with GemFire ! 
date: 2025-06-01 01:01:0 01:01:01 +/-TTTT
categories: [gemfire, cache, springboot, postgresql, caching]
tags: [gemfire, cache, springboot, postgresql, caching]
---

<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-Q2P5CM1K51"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-Q2P5CM1K51');
</script>

<script data-goatcounter="https://arulwebsite.goatcounter.com/count"
        async src="//gc.zgo.at/count.js"></script>


<script>
    // Append to the <body>; can use a CSS selector to append somewhere else.
    window.goatcounter.visit_count({append: 'body'})
</script>


# 🚀 Turbocharge Your Spring Boot App: Inline Caching with GemFire & PostgreSQL


## 🌟 Introduction: The Need for Speed and Scalability

In today's data-intensive applications, performance is paramount. Repeatedly hitting your database for the same piece of information can quickly become a bottleneck, leading to slow response times and increased load on your persistent storage. This is where inline caching shines!

This project, inline-caching-gemfire-postgres, demonstrates a powerful solution: leveraging VMware Tanzu GemFire (built on Apache Geode) as an in-memory data grid to supercharge a Spring Boot application. By intelligently caching frequently accessed data, we drastically reduce database round trips, resulting in a faster, more responsive, and scalable application.

### Why Inline Caching?

Imagine an e-commerce site where product details are viewed millions of times a day. Fetching "Product X" from PostgreSQL every single time is inefficient. With inline caching:

- First Request: The application checks the cache. It's not there, so it fetches "Product X" from PostgreSQL.

- Caching: "Product X" is then stored in GemFire.

- Subsequent Requests: The application checks the cache, finds "Product X" instantly, and returns it without ever touching the database.

This strategy leads to:

Blazing Fast Reads: Data retrieved from RAM is orders of magnitude faster than from disk.
Reduced Database Load: Your PostgreSQL instance can focus on writes and less frequent reads.
Improved Scalability: Your application can handle more concurrent requests for cached data.

### ✨ Project Features

Inline Caching with GemFire: Seamlessly integrates GemFire as a high-performance, distributed cache.

- Spring Boot Powered: Built on the robust Spring Boot framework for rapid development and easy configuration.

- RESTful API: A straightforward API to interact with DataItem entities.

- PostgreSQL Persistence: Utilizes PostgreSQL as the reliable backend for persistent data storage.

Comprehensive Setup: Detailed instructions for setting up GemFire and PostgreSQL, including Docker.

### 📊 Data Model
Our application uses a simple data_item table in PostgreSQL to store our data:

```
Column	Type	Description
id	SERIAL	Primary key, auto-incremented
name	VARCHAR(255)	Name of the item
description	TEXT	Detailed description of the item
```

The corresponding Java model is located at com.example.model.DataItem. This DataItem class represents the objects that will be stored in both PostgreSQL and GemFire.

### 🚀 Getting Started

Follow these steps to get the inline-caching-gemfire-postgres project up and running on your local machine.

### Prerequisites

Before you begin, ensure you have the following installed:

- Java 21
- Apache Maven (version 3.x or higher)
- Docker Desktop (for running PostgreSQL and GemFire)
- 🔑 Broadcom GemFire Repository Access
VMware Tanzu GemFire dependencies (which Spring Boot for GemFire relies on) are hosted on the Broadcom Support Portal. You'll need to configure your Maven environment to access them.

Create a Broadcom Support Portal Account: If you don't have one, visit the Broadcom Support Portal and register.

Generate an Access Token:

Log in to the Broadcom Support Portal.
Navigate to My Downloads.
Search for "VMware Tanzu GemFire" and click on the product.
Scroll down to Show All Releases.
Look for a section related to Repository Access and click the green token symbol (it might be labeled "Repository Access Token" or similar).
Note your email address (your username for Maven) and copy your access_token (ensure you don't copy any surrounding quotation marks).
Add GemFire Repository to pom.xml:
Make sure your project's pom.xml contains the following repository definition. (It should already be there in this project.)

```
<repositories>
    <repository>
        <id>gemfire-release-repo</id>
        <name>Broadcom GemFire Release Repository</name>
        <url>https://packages.broadcom.com/artifactory/gemfire/</url>
    </repository>
</repositories>
```

Add Broadcom Credentials to .m2/settings.xml:

Maven uses settings.xml (typically located at ~/.m2/settings.xml) to store repository credentials. Add the following server block, replacing MY-USERNAME@example.com with your Broadcom Support Portal email and MY-ACCESS-TOKEN with the token you generated.

```
<settings>
    <servers>
        <server>
            <id>gemfire-release-repo</id>
            <username>MY-USERNAME@example.com</username>
            <password>MY-ACCESS-TOKEN</password>
        </server>
    </servers>
</settings>
```

If you don't have a settings.xml file, create one in the ~/.m2/ directory.

### 🛠️ Installation & Setup
1. Clone the Repository
First, get the project code onto your machine:

```
git clone https://github.com/cfkubo/inline-caching-gemfire-postgres.git
cd inline-caching-gemfire-postgres
```

### 1. PostgreSQL Setup
   
We'll run PostgreSQL using Docker for simplicity.

```
docker run -d --name postgres-db \
-p 5432:5432 \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=mydatabase \
--network host \
debezium/example-postgres:2.3.3.Final # Or any standard PostgreSQL image
```


### Explanation of Docker Run Command:
- docker run -d --name postgres-db: Runs PostgreSQL as a detached (background) process.
--name postgres-db: Assigns a name to your container.
-p 5432:5432: Maps the container's PostgreSQL port to your host machine.
-e POSTGRES_USER=postgres, -e POSTGRES_PASSWORD=postgres, -e POSTGRES_DB=mydatabase: Sets up the database credentials and initial database.
--network host: Allows the Spring Boot app on your host to directly connect to localhost:5432. If you run the Spring Boot app in Docker later, you'd put both on a custom Docker network.
1. Create Table and Sample Data
Connect to your PostgreSQL database (e.g., using psql, DBeaver, or pgAdmin) and run the following SQL statements to create the data_item table and populate it with sample data:

-- Create the table

```
CREATE TABLE data_item (
    id SERIAL PRIMARY KEY, -- 'id' will automatically generate unique, sequential integers
    name VARCHAR(255) NOT NULL,
    description TEXT
);
```

-- Insert a single, specific item

```
INSERT INTO data_item (name, description) VALUES ('Widget', 'A useful widget for everyday tasks.');
```

-- Insert 1000 random items for robust testing

```
INSERT INTO data_item (name, description)
SELECT
    'Product ' || SUBSTRING(MD5(RANDOM()::TEXT) FROM 1 FOR 3) || FLOOR(RANDOM() * 9000 + 1000)::TEXT,
    'This is a randomly generated description for item ' || gs.i || '. It highlights various attributes and features, such as ' ||
    CASE FLOOR(RANDOM() * 3)
        WHEN 0 THEN 'durability'
        WHEN 1 THEN 'efficiency'
        ELSE 'ease of use'
    END ||
    ', and is ideal for ' ||
    CASE FLOOR(RANDOM() * 4)
        WHEN 0 THEN 'home users.'
        WHEN 1 THEN 'professional environments.'
        WHEN 2 THEN 'outdoor adventures.'
        ELSE 'everyday tasks.'
    END ||
    ' Additional unique identifier: ' || MD5(RANDOM()::TEXT)
FROM generate_series(1, 1000) as gs(i);
```


1. Build the Spring Boot Project
Navigate to the root of your cloned repository and build the project using Maven:

```
mvn clean install
```
This command compiles the code, runs tests, and packages your application into a JAR file.

1. Configure Database Connection
Open src/main/resources/application.properties and update the database connection details to match your PostgreSQL setup:

Properties

```
# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=postgres
spring.datasource.password=postgres

# Show SQL for debugging (optional, but useful to see DB hits)
spring.jpa.show-sql=true
```

6. GemFire Setup
We'll run GemFire locally using Docker. The official GemFire Docker images are a quick way to get started.

a. Start a GemFire Locator:
The locator acts as the discovery service for your GemFire cluster.


gfsh

Once in gfsh, connect to your locator:

```
connect --locator=localhost[10334]
```


Then, create the dataItemRegion. We'll use PARTITION for horizontal scalability.

```
create region --name=dataItemRegion --type=PARTITION
```

This command creates a distributed region named /dataItemRegion across your GemFire cluster, ready to store your cached items.

1. Run the Spring Boot Application
Finally, start your Spring Boot application:

```
mvn spring-boot:run
```

Your application should start up and connect to both PostgreSQL and your GemFire cluster.

### 💡 How Inline Caching Works in this Project
This project implements a cache-aside pattern, which is a common strategy for inline caching.

Client Request: A request comes into the Spring Boot application (e.g., GET /data/{id}).
Cache Lookup: The application's service layer first checks if the DataItem with the requested id is present in the dataItemRegion in GemFire.
Cache Hit: If the item is found in GemFire (a "cache hit"), it's immediately returned to the client. This is extremely fast!
Cache Miss: If the item is not found in GemFire (a "cache miss"), the application then queries PostgreSQL to fetch the DataItem.
Cache Population: Once the DataItem is retrieved from PostgreSQL, it's immediately stored in the dataItemRegion in GemFire.
Return Data: The DataItem is then returned to the client. Subsequent requests for the same id will now result in a cache hit.
This intelligent flow ensures that your database is only queried when necessary, reducing load and improving responsiveness.

### 🧪 Usage and Testing the Cache
Access the application at http://localhost:8080 (or http://localhost:9989 if your server.port is configured to 9989 in application.properties).

The core REST endpoint is designed to fetch data by ID: GET http://localhost:8080/data/{id}.

Let's test the caching behavior:

First Request (Cache Miss):
Make a GET request for an ID that hasn't been fetched yet (e.g., id=1 if it's the first time you're hitting it, or any other ID from your 1000 generated items).


```
curl -i http://localhost:8080/data/1
```
Observe: In your Spring Boot application logs (where mvn spring-boot:run is running), you should see a SQL query being executed (because spring.jpa.show-sql=true). This confirms the data was loaded from PostgreSQL.
Subsequent Request (Cache Hit):
Immediately make the exact same GET request for the same ID.


```
curl -i http://localhost:8080/data/1
```

Observe: In your Spring Boot application logs, you should NOT see a SQL query being executed for this request. This confirms that the data was successfully served directly from the GemFire cache!
Fetch New Data:
Try fetching data with a different ID (e.g., id=200).


```
curl -i http://localhost:8080/data/200
```
Observe: You will again see a SQL query in the logs for the first time you fetch id=200, as it's a new item being cached. Subsequent requests for id=200 will then be served from GemFire.


### 📝 GemFire Setup Notes
Server and Region First: It's critical that your GemFire locator and server are running, and the dataItemRegion is created before you start your Spring Boot application. The Spring Boot application acts as a GemFire client and needs to connect to an existing cluster with the region defined.
Region Type: We used PARTITION for the dataItemRegion. This type horizontally distributes data across multiple GemFire servers, which is great for scalability. You could also use REPLICATE if you need every piece of data on every server for high availability (but it uses more memory).
GemFire Docker Quickstart: For more advanced GemFire Docker setups or troubleshooting, refer to the official GemFire Docker quickstart guide.

### ✅ Conclusion
This project serves as a clear, practical example of implementing inline caching with VMware Tanzu GemFire in a Spring Boot application. By intelligently leveraging an in-memory data grid, you can significantly enhance your application's read performance, reduce database load, and improve overall scalability.

Feel free to experiment with different data volumes, cache eviction policies, and observe how GemFire truly transforms your application's performance!
