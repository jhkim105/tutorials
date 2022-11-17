Spring Cloud Microservices
===============================

Spring Cloud 활용하여 마이크로 서비스 구현하기
- OAuth2
- Config Server
- Gateway
- Feign
- Sleuth / Zipkin

## OAuth2

### Authorization Server
Keycloak

#### Get AccessToken 
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

### Product Service
- OAuth2 Resource Server

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

### GateWay
- Spring Cloud Gateway
- OAuth2 Client
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
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
spring:
  config:
    import: 'optional:configserver:'
  application:
    name: gateway
  cloud:
    gateway:
      routes:
        - id: product
          uri: http://localhost:8081
          predicates:
            - Path=/products/**
      discovery:
        locator:
          enabled: true
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: oidc-demo
            client-secret: Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt
            authorization-grant-type: authorization_code
            scope: openid, read
            redirect-uri: http://localhost:8080/login/oauth2/code/keycloak
        provider:
          keycloak:
            issuer-uri: http://localhost:8089/realms/demo
            end-session-endpoint: ${issuer-uri}/protocol/openid-connect/logout
            user-name-attribute: preferred_username
```

```java
@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

  private final ReactiveClientRegistrationRepository clientRegistrationRepository;

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception  {
    http.csrf().disable()
        .authorizeExchange(auth -> auth
            .pathMatchers("/").permitAll()
            .anyExchange().authenticated())
        .oauth2Login(Customizer.withDefaults())
        .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
    return http.build();
  }

  private ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
        new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository);

    oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
    return oidcLogoutSuccessHandler;
  }

}
```

http://localhost:8080/me

## Discovery
http://localhost:8761




## Refs
https://www.baeldung.com/spring-cloud-gateway-oauth2
https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-bootstrap
https://developer.okta.com/blog/2020/08/14/spring-gateway-patterns
