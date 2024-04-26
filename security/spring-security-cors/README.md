# Spring Security CORS
Spring Security Filter 에서 동작. 전역에서 동작하게 하려면 CorsFilter 를 정의하거나, WebMvcConfigurer.addCorsMappings() 을 override 해야 한다.

## CORS Configuration
```text
http.cors(Customizer.withDefaults());
```
```java
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
```


## Refs
https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html

