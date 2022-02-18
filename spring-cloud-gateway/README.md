

## Route Configuration 
```yaml
spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      routes:
        - id: one
          uri: http://localhost:7777
          predicates:
            - Path=/one

```


## Route Configuration By Java DSL

```java
  @Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
    .route("path_route", r -> r.path("/two")
    .uri("http://localhost:7777"))
    .build();
    }
```



## MockServer
### Run
```shell
mockserver -logLevel INFO -serverPort 7777
```


#### Create Rule
```shell
curl -v -X PUT "http://localhost:7777/mockserver/expectation" -d '{
  "httpRequest" : {
    "path" : "/one"
  },
  "httpResponse" : {
    "body" : "One"
  }
}'

curl -v -X PUT "http://localhost:7777/mockserver/expectation" -d '{
  "httpRequest" : {
    "path" : "/two"
  },
  "httpResponse" : {
    "body" : "Two"
  }
}'
```


## Simple Gateway By Spring MVC OR WebFlux

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-gateway-webflux</artifactId>
    </dependency>
```

```java
  @GetMapping("/proxy")
  public Mono<ResponseEntity<byte[]>> proxy(ProxyExchange<byte[]> proxy) throws Exception {
    return proxy.uri("http://localhost:7777/one").get();
  }
```


## References
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/


