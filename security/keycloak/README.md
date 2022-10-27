Keycloak
======================


## Setting Up a Keycloak Server

### Run Docker
```shell
docker run -d -p 8080:8080 --name=keycloak \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:19.0.3 start-dev
```

### Admin Console
http://localhost:8089/
admin/admin


## Refs
https://www.keycloak.org/getting-started/getting-started-docker
https://www.baeldung.com/spring-boot-keycloak

