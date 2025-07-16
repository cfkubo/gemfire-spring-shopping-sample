FROM eclipse-temurin:21-jre
COPY target/inline-caching-gemfire-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
