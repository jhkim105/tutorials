Spring Cloud Microservices
===============================

Spring Cloud 활용하여 마이크로 서비스 구현하기
- Config Server
- Discovery
- OAuth2
- Gateway
- Feign
- Sleuth / Zipkin

## Discovery

### Eureka Server
```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
```
```yaml
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false

```

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {
```
http://localhost:8761

### Eureka Client

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
```

```yaml
eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    prefer-ip-address: true
```


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


### Service to Service

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
  public WebClient productWebClient(OAuth2AuthorizedClientManager authorizedClientManager) {
    ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    return WebClient.builder()
        .baseUrl("http://localhost:8081")
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
        .uri("/products")
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

## OpenFeign OAuth2 Support 

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
```

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

FeignConfig.java
```java
public class FeignConfig {

  public static final String CLIENT_REGISTRATION_ID = "api-client";

  @Bean
  public RequestInterceptor requestInterceptor(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientManager authorizedClientManager) {
    ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(CLIENT_REGISTRATION_ID);
    OAuthClientCredentialsFeignManager clientCredentialsFeignManager =
        new OAuthClientCredentialsFeignManager(authorizedClientManager, clientRegistration);
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
    };
  }

  @Bean
  OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
    OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
        .clientCredentials()
        .build();

    AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
        new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    return authorizedClientManager;
  }

}
```

```java
public class OAuthClientCredentialsFeignManager {

  private final OAuth2AuthorizedClientManager manager;
  private final Authentication principal;
  private final ClientRegistration clientRegistration;

  public OAuthClientCredentialsFeignManager(OAuth2AuthorizedClientManager manager, ClientRegistration clientRegistration) {
    this.manager = manager;
    this.clientRegistration = clientRegistration;
    this.principal = createPrincipal();
  }

  private Authentication createPrincipal() {
    return new Authentication() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
      }

      @Override
      public Object getCredentials() {
        return null;
      }

      @Override
      public Object getDetails() {
        return null;
      }

      @Override
      public Object getPrincipal() {
        return this;
      }

      @Override
      public boolean isAuthenticated() {
        return false;
      }

      @Override
      public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
      }

      @Override
      public String getName() {
        return clientRegistration.getClientId();
      }
    };
  }

  public String getAccessToken() {
    try {
      OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
          .withClientRegistrationId(clientRegistration.getRegistrationId())
          .principal(principal)
          .build();
      OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);
      if (isNull(client)) {
        throw new IllegalStateException("client credentials flow on " + clientRegistration.getRegistrationId() + " failed, client is null");
      }
      return client.getAccessToken().getTokenValue();
    } catch (Exception ex) {
      throw new IllegalStateException("client credentials error " + ex.getMessage(), ex);
    }
  }
}
```

OrderClient.java
```java
@FeignClient(value = "order-service", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface OrderClient {

    @RequestMapping(value = "/orders", method = {RequestMethod.GET})
    List<Order> getAll();

}

```

```java
@SpringBootApplication
@EnableFeignClients
public class ProductServiceApplication {
```


## Refs
https://www.baeldung.com/spring-cloud-gateway-oauth2  
https://github.com/eugenp/tutorials/tree/master/spring-cloud-modules/spring-cloud-bootstrap  
https://developer.okta.com/blog/2020/08/14/spring-gateway-patterns  
