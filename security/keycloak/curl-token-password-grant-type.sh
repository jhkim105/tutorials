curl -L -X POST \
  'http://localhost:8089/realms/demo/protocol/openid-connect/token' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'client_id=oidc-demo' \
  -d 'client_secret=Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt' \
  -d 'grant_type=password' \
  -d 'scope=read' \
  -d 'username=user01' \
  -d 'password=pass01' \
  | python -mjson.tool