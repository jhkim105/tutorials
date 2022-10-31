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

### Get AccessToken
```shell
curl http://localhost:8089/auth/realms/demo/protocol/openid-connect/token /
	-d "client_id=oidc-login-app" \
	-d "username=user01&password=user01" \
	-d "grant_type:password"
```

## Refs
https://www.keycloak.org/getting-started/getting-started-docker
https://www.baeldung.com/spring-boot-keycloak

