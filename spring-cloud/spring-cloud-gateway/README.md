

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

## Filter
### GatewayFilter Factories
Http Request, Response 를  수정할 수 있게 해주는 내장 필터
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/#gatewayfilter-factories

### Global Filters
모든 경로에 조건부로 적용되는 필터


### Custom GatewayFilter Factories

- AbstractGatewayFilterFactory 를 상속받아서 구현한다. 
- 이름은 GatewayFilterFactory 로 끝나야 한다. 

## References
https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/
https://www.baeldung.com/spring-cloud-gateway-webfilter-factories
https://www.baeldung.com/spring-cloud-custom-gateway-filters

