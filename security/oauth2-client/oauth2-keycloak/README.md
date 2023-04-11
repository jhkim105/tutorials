OAuth2 Client - Keycloak
================================

## Authorization Server
Keycloak

## OAuth2 Client

### Configuration
pom.xml
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
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: oauth2-client-demo
            authorization-grant-type: authorization_code
            scope: openid
        provider:
          keycloak:
            issuer-uri: http://localhost:8089/realms/demo
            user-name-attribute: preferred_username

```

SecurityConfig.java
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
    return http.build();
  }


}
```

http://localhost:8080/user-info

### Social Logout

SecurityConfig
- OidcClientInitiatedLogoutSuccessHandler 지정
```java
  @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
    http
    .authorizeHttpRequests(auth -> auth
    .antMatchers("/login").permitAll()
    .anyRequest().authenticated());

    http.oauth2Login();
    http.logout(logout -> logout
    .logoutSuccessHandler(oidcLogoutSuccessHandler())
    .logoutSuccessUrl("/"));
    return http.build();
    }

    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    successHandler.setPostLogoutRedirectUri("{baseUrl}");
    return successHandler;
    }
```


## Refs
https://www.baeldung.com/spring-boot-keycloak
