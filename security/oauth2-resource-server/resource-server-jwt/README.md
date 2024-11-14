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
- issuer-uri + "/well-known/openid-configuration" 를 호출해서 인증관련 정보를 조회함(JwtDecoders.java)
- http://localhost:8089/realms/demo/.well-known/openid-configuration

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

## How It Works
JwtAuthenticationProvider      
jwk-set-uri 를 지정하면 인증서버가 내려가 있어도 동작한다. 키 정보를 해당 uri 에서 가져와서 캐싱해서 사용.


## Refs
https://www.baeldung.com/spring-security-oauth-resource-server