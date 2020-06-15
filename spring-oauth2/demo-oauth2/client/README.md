# Spring Boot OAuth2 Client


## Client Setting for Custom OAuth Provider
pom.xml
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>
```

application.properties
```properties
# oauth2
spring.security.oauth2.client.registration.myoauth.client-id=client01
spring.security.oauth2.client.registration.myoauth.client-secret=secret01
spring.security.oauth2.client.registration.myoauth.client-name=client01
spring.security.oauth2.client.registration.myoauth.provider=myoauth-provider
spring.security.oauth2.client.registration.myoauth.scope=read
spring.security.oauth2.client.registration.myoauth.redirect-uri=http://localhost:8080/authorization-code
spring.security.oauth2.client.registration.myoauth.client-authentication-method=authorization_code
spring.security.oauth2.client.registration.myoauth.authorization-grant-type=authorization_code

spring.security.oauth2.client.provider.myoauth-provider.authorization-uri=http://localhost:8081/oauth/authorize
spring.security.oauth2.client.provider.myoauth-provider.token-uri=http://localhost:8081/oauth/token
spring.security.oauth2.client.provider.myoauth-provider.user-info-uri=http://localhost:8081/oauth/token
spring.security.oauth2.client.provider.myoauth-provider.user-info-authentication-method=header
spring.security.oauth2.client.provider.myoauth-provider.jwk-set-uri=http://localhost:8081/token_keys
spring.security.oauth2.client.provider.myoauth-provider.user-name-attribute=name
```


## Refs
[Spring Boot and OAuth](https://spring.io/guides/tutorials/spring-boot-oauth2/)
[git-simple](https://github.com/spring-guides/tut-spring-boot-oauth2/tree/master/simple)
[Spring Boot - OAuth2](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-security-oauth2)
