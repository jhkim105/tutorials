OAuth2 Resource Server - Using JWT
===================

## Configuration

Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    <version>2.7.5</version>
</dependency>
```

application.yml
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8089/realms/demo
```

SecurityConfig.java
```java
  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return
        http
            .authorizeRequests(authorize -> authorize
              .antMatchers(HttpMethod.GET, "/products/**").hasAnyAuthority("email", "SCOPE_email")
              .anyRequest().authenticated()
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .build();
  }
```

## Refs
https://www.baeldung.com/spring-security-oauth-resource-server