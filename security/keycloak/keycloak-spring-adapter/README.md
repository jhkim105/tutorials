Example for using the keycloak Spring Security Adapter
===============================

keycloak-spring-security-adapter 가 Spring security 6 을 지원하지 않으므로, Spring Boot 2.7.5 버전을 사용하였다.

##

### Dependencies
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
  <groupId>org.keycloak</groupId>
  <artifactId>keycloak-spring-boot-starter</artifactId>
  <version>20.0.3</version>
</dependency>
```

### Configuration
SecurityConfig.java
```java
@KeycloakConfiguration
@RequiredArgsConstructor
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  private final KeycloakClientRequestFactory keycloakClientRequestFactory;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/admin/**").hasAnyRole("server")
        .anyRequest().authenticated();

  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }
  
}

```

Spring boot config (application.properties) 를 사용하기 위해 KeycloakSpringBootConfigResolver 등록  
KeycloakConfigResolverConfig.java
```java
@Configuration
public class KeycloakConfigResolverConfig {


  @Bean
  public KeycloakConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }


}
```

application.properties
```properties
keycloak.realm=demo
keycloak.auth-server-url=http://localhost:8089
keycloak.resource=keycloak-spring-adapter
keycloak.credentials.secret=vUt7y5LD9PptYQIKAidn3haDxjFd1MKG
keycloak.use-resource-role-mappings=true
```
keycloak.use-resource-role-mappings=true 를 주면 토큰의 resource_access.<client_id>.roles 에서 role 을 매핑한다.  
User 와 Client 둘 다 권한관리를 하려면 resource-role-mappings 를 사용하고, User/Client 에 client role 을 할당한다. 


## Refs
https://www.keycloak.org/docs/latest/securing_apps/#_spring_boot_adapter
https://www.keycloak.org/docs/latest/securing_apps/#_spring_security_adapter