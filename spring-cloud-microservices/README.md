Spring Cloud Microservices
===============================

Spring Cloud 활용하여 마이크로 서비스 구현하기
- OAuth2
- Gateway
- Feign
- Sleuth / Zipkin

## Authentication / Authorization

### Authorization Server
Keycloak

### Get AccessToken 
```shell
curl -L -X POST \
'http://localhost:8089/realms/demo/protocol/openid-connect/token' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=oidc-demo' \
--data-urlencode 'client_secret=Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'scope=read' \
--data-urlencode 'username=user01' \
--data-urlencode 'password=pass01'
```

### Resource Server - product-service

application.yml
```yaml
server:
  port: 8081
spring:
  security:
    oauth2:
      resourceserver:
        opaque:
          introspection-uri: http://localhost:8089/realms/demo/protocol/openid-connect/token/introspect
          introspection-client-id: oidc-demo
          introspection-client-secret: Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt
```

### Client - web

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

```yaml
server:
  port: 8080
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: oidc-demo
            client-secret: Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt
            authorization-grant-type: authorization_code
            scope: openid, read
        provider:
          keycloak:
            issuer-uri: http://localhost:8089/realms/demo
            user-name-attribute: preferred_username
```

```java
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final KeycloakLogoutHandler keycloakLogoutHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
    http
        .authorizeHttpRequests(auth -> auth
                .antMatchers("/user-info").hasRole("USER")
                .anyRequest().permitAll());
    http.oauth2Login();
    http.logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"));
    return http.build();
  }


}
```
### Gateway
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
```

```yaml
server:
  port: 7070
logging:
  level:
    'org.springframework.cloud.gateway': info
spring:
  cloud:
    gateway:
      routes:
        - id: product
          uri: http://localhost:8081
          predicates:
            - Path=/products/**
        - id: default
          uri: http://localhost:8080
          predicates:
            - Path=/**
```

Test
```http request
GET http://localhost:7070/products
Authorization: Bearer {{access_token}}

```


## Refs
https://www.baeldung.com/spring-cloud-gateway-oauth2