Resource Server - Using Opaque Token
=====================================

## Configuration
pom.xml
```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.nimbusds/oauth2-oidc-sdk -->
    <dependency>
      <groupId>com.nimbusds</groupId>
      <artifactId>oauth2-oidc-sdk</artifactId>
      <version>10.1</version>
      <scope>runtime</scope>
    </dependency>
```

application.yml
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        opaque:
          introspection-uri: http://localhost:8089/realms/demo/protocol/openid-connect2/token/introspect
          introspection-client-id: oidc-demo
          introspection-client-secret: Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt
```

SecurityConfig.java
```java

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return
        http
            .authorizeRequests(authorize -> authorize
                .antMatchers(HttpMethod.GET, "/products/**").hasAuthority("SCOPE_read")
                .anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(token -> token
                      .introspectionUri(this.introspectionUri)
                      .introspectionClientCredentials(this.clientId, this.clientSecret)))
            .build();
  }
```
## How It Works
OpaqueTokenAuthenticationProvider  
NimbusOpaqueTokenIntrospector

### Looking-Up Attributes Post-Authentication
```java
  @GetMapping("/authinfo")
  public Map<String, Object> authinfo(BearerTokenAuthentication authentication) {
    return authentication.getTokenAttributes();
  }

```

```java
  @GetMapping("/authinfo2")
  public Map<String, Object> authinfo2(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
    return principal.getAttributes();
  }
```


## Refs
https://www.baeldung.com/spring-security-oauth-resource-server#opaque-server
https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/opaque-token.html

