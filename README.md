# hazelcast-viewer

## Run

This project is a Maven-built Spring Boot application, so can be run in many ways:

* With the Application class via an IDE or command line
* With the Maven-built jar file: `java -jar target/hazelcast-viewer-<version>.jar`
* With Maven: `mvn spring-boot:run`

## Endpoints

The following endpoints are available:

* `GET /maps` - get a list of map names
* `GET /maps/{mapName}` - get the full contents of a map
* `GET /maps/{mapName}/{mapKey}` - get a specific entry from a map

Spring Boot actuators are also enabled, so all [actuator endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) are available, authentication for these endpoints is disabled.

# Viewing serialized objects

If the Hazelcast instance is storing serialized objects, you can add support for these by adding them as Maven dependencies to this project.

This will allow you view custom Java objects from your project that are stored in Hazelcast.