curl -L -X POST \
  'http://localhost:8089/realms/demo/protocol/openid-connect/token' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'client_id=oidc-demo' \
  --data-urlencode 'client_secret=Muo0SyBXyd3z06G5YPuP4n4gggX8pQlt' \
  --data-urlencode 'grant_type=password' \
  --data-urlencode 'scope=read' \
  --data-urlencode 'username=user01' \
  --data-urlencode 'password=pass01'