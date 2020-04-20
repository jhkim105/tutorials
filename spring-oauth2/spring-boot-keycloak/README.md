# Spring Boot Keycloak

## Setting Up a Keycloak Server
### Downloads and Install
https://downloads.jboss.org/keycloak/9.0.3/keycloak-9.0.3.tar.gz

```
tar xvzf keycloak-9.0.3.tar.gz
cd keycloak-9.0.3/bin
./standalone.sh
```

### Creating Admin User
http://localhost:8080/auth

### Login to the admin console and Setting Up 

1. Creating a Realm
2. Creating a Client
3. Creating a Role and User


## Creating a Spring Boot Application

### Dependencies
```
<dependency>
    <groupId>org.keycloak</groupId>
    <artifactId>keycloak-spring-boot-starter</artifactId>
</dependency>
```



## References
[Keycloak](https://www.keycloak.org/)
