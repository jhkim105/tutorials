Spring Authorization Server
====================

## Maven Dependencies

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```

## Configuration

```yaml
spring:
  security:
    user:
      name: user01
      password: pass01
    oauth2:
      authorizationserver:
        client:
          oidc-client:
            registration:
              client-id: "oidc-client"
              client-secret: "{noop}secret"
              client-authentication-methods:
                - "client_secret_basic"
              authorization-grant-types:
                - "authorization_code"
                - "refresh_token"
              redirect-uris:
                - "http://127.0.0.1:8080/authorized"
              post-logout-redirect-uris:
                - "http://127.0.0.1:8080/"
              scopes:
                - "openid"
                - "profile"
            require-authorization-consent: true
```

## Supported Grant Type
OAuth2TokenEndpointFilter 에서 3가지 type 의 Converter 를 등록함
- OAuth2AuthorizationCodeAuthenticationConverter
- OAuth2RefreshTokenAuthenticationConverter
- OAuth2ClientCredentialsAuthenticationConverter

Password GrantType 은 지원안함

## Userinfo
- Userinfo endpoint 를 제공
```text
 GET /userinfo HTTP/1.1
  Authorization: Bearer SlAV32hkKG
```
- Customize
  - https://docs.spring.io/spring-authorization-server/reference/guides/how-to-userinfo.html


## References
- [Spring Authorization Server](https://docs.spring.io/spring-authorization-server/reference/index.html)
