# OAuth2 Authorization Server


## Basic
pom.xml
```xml
      <depedency>
        <groupId>org.springframework.security.oauth.boot</groupId>
        <artifactId>spring-security-oauth2-autoconfigure</artifactId>
        <version>2.2.6.RELEASE</version>
      </dependency>

```

application.properties
```properties
# Security
spring.security.user.name=user01
spring.security.user.password=pass01
#security.basic.enabled=false

# OAuth2Server
security.oauth2.client.client-id=client01
security.oauth2.client.client-secret=secret01
security.oauth2.client.scope=read, write
security.oauth2.authorization.check-token-access=permitAll()
security.oauth2.authorization.token-key-access=permitAll()
```

