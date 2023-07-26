Keycloak
======================


## Setting Up a Keycloak Server

### Run Docker
```shell
docker run -d -p 8089:8080 --name=keycloak \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:19.0.3 start-dev
```

### Admin Console
http://localhost:8089/
admin/admin

### Get AccessToken
```shell
curl 'http://localhost:8089/realms/demo/protocol/openid-connect/token' \
	-H 'Content-Type: application/x-www-form-urlencoded' \
	-d 'client_id=oidc-demo' \
	-d 'client_secret=Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt' \
	-d 'grant_type=password' \
	-d 'scope=read' \
	-d 'username=user01' \
	-d 'password=pass01'
```
-u 옵션을 사용해서 인증정보를 헤더로 보내기
```shell
curl 'http://localhost:8089/realms/demo/protocol/openid-connect/token' \
	-H 'Content-Type: application/x-www-form-urlencoded' \
    -u 'oidc-demo:Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt' \
	-d 'grant_type=password' \
	-d 'scope=read' \
	-d 'username=user01' \
	-d 'password=pass01'
```

## Refs
https://www.keycloak.org/getting-started/getting-started-docker
https://www.baeldung.com/spring-boot-keycloak

