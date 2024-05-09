



### Prometheus Endpoint
```xml
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
```
GET http://localhost:9091/actuator
```shell
    "prometheus": {
      "href": "http://localhost:9091/actuator/prometheus",
      "templated": false
    },
```

## Refs
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
- https://www.baeldung.com/spring-boot-actuator-enable-endpoints


