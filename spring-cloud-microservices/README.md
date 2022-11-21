Spring Cloud Microservices
===============================

Spring Cloud 활용하여 마이크로 서비스 구현하기
- Config Server
- Discovery
- OAuth2
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


### GateWay
- Spring Cloud Gateway
- OAuth2 Resource Server
- OAuth2 Client

http://localhost:8080/me


### Server to Server

```yaml
      client:
        registration:
          api-client:
            provider: keycloak
            client-id: oidc-demo
            client-secret: Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt
            authorization-grant-type: client_credentials
            scope: read
        provider:
          keycloak:
            token-uri: http://localhost:8089/realms/demo/protocol/openid-connect/token
```

WebClientConfig.java
```java
@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    return WebClient.builder()
        .apply(oauth2Client.oauth2Configuration())
        .build();
  }

  @Bean
  public OAuth2AuthorizedClientManager authorizedClientManager(
      ClientRegistrationRepository clientRegistrationRepository,
      OAuth2AuthorizedClientRepository authorizedClientRepository) {

    OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
        .clientCredentials()
        .build();

    DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
        clientRegistrationRepository, authorizedClientRepository);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }


}

```

ProductController.java
```java
  private final WebClient webClient;

  @GetMapping
  public List<Product> getProducts() {
    return this.webClient
        .get()
        .uri("http://localhost:8081/products")
        .attributes(clientRegistrationId("api-client"))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
        .block();
  }

```

## Tracing -  Sleuth and Zipkin

### Run Zipkin Server
```shell
docker run -d --name=zipkin -p 9411:9411 openzipkin/zipkin
```
http://localhost:9411

### Client Configuration

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-sleuth-zipkin -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-sleuth-zipkin</artifactId>
      <version>3.1.5</version>
    </dependency>
```

```yaml
spring:
  sleuth:
    sampler:
      probability: 1.0
```



## Refs
https://www.baeldung.com/spring-cloud-gateway-oauth2
https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-bootstrap
https://developer.okta.com/blog/2020/08/14/spring-gateway-patterns
