

### Create a private RSA keys
PKCS#1
```shell
openssl genrsa -out rsa-private.pem 2048
```

PKCS#8
```shell
openssl genpkey -algorithm RSA -out rsa-private-pkcs8.pem -pkeyopt rsa_keygen_bits:2048
```

PKCS#1 -> PKCS#8
```shell
openssl pkcs8 -topk8 -in rsa-private.pem -out rsa-private-pkcs8-key.pem -nocrypt
```

### Extract a public key from the private key
```shell
openssl rsa -pubout -in rsa-private.pem  -out rsa-public-key.pem
openssl rsa -pubout -in rsa-private-pkcs8.pem  -out rsa-public-key-of-pkcs8.pem
```

