## RSA

### Generate RSA Keys

#### Create a private RSA keys
PKCS#1
```shell
openssl genrsa -out rsa-private.pem 2048
```

PKCS#8
```shell
openssl genpkey -algorithm RSA -out rsa-private-pkcs8 -pkeyopt rsa_keygen_bits:2048
```

PKCS#1 -> PKCS#8
```shell
openssl pkcs8 -topk8 -in rsa-private.pem -out rsa-private-pkcs8.pem -nocrypt
```

Extract a public key from the private key
```shell
openssl rsa -in rsa-private.pem -pubout -outform PEM -out rsa-public.pem
```

### Generate a JWT with RSA keys

#### Header
```shell
echo -n '{"alg":"RS256","typ":"JWT"}' | base64 | sed s/\+/-/ | sed -E s/=+$//
```
```text
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9
```

#### Payload
```shell
echo -n '{"sub":"RS256inOTA","name":"John Doe"}' | base64 | sed s/\+/-/ | sed -E s/=+$//
```

```text
eyJzdWIiOiJSUzI1NmluT1RBIiwibmFtZSI6IkpvaG4gRG9lIn0
```

#### Signature
{header}.{payload} 를 private key 로 signature 를 생성
```shell
echo -n "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSUzI1NmluT1RBIiwibmFtZSI6IkpvaG4gRG9lIn0" | openssl dgst -sha256 -binary -sign rsa-private.pem  | openssl enc -base64 | tr -d '\n=' | tr -- '+/' '-_'
```

```text
Do0-ugiYm4AA84C750bnr-QoIeblwnyzkwptVZkmfK4BDmVqE25duZLNhZG-Y5_7qhz842VOjJp2aTD9oevNfNxfLntxs5ToJivorh2dCwr_j99R4A-yA1YMdVs1vUGmoBb-TVYwE2jOPcsc8P_Osqnep8boEGQzE6zIOnRJO3OwP7PxsHefqwnB2TcBSRkJHGZXWTC889ftUIwPFwDYcu1OcfoTv1MxH2oU3DMSUxT46MapB_FhJnIgN6vNoEr3X-hr4vGENvbl9qN9dGHv8IXjfeDdMLuqDr1alaYOHIPmQuPu1iJ-8_jBUOSUizIY4QFcxFhaMNgYl_GrTD30yw
```


### A complete JWT
{header}.{payload}.{signature}
```text
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSUzI1NmluT1RBIiwibmFtZSI6IkpvaG4gRG9lIn0.Do0-ugiYm4AA84C750bnr-QoIeblwnyzkwptVZkmfK4BDmVqE25duZLNhZG-Y5_7qhz842VOjJp2aTD9oevNfNxfLntxs5ToJivorh2dCwr_j99R4A-yA1YMdVs1vUGmoBb-TVYwE2jOPcsc8P_Osqnep8boEGQzE6zIOnRJO3OwP7PxsHefqwnB2TcBSRkJHGZXWTC889ftUIwPFwDYcu1OcfoTv1MxH2oU3DMSUxT46MapB_FhJnIgN6vNoEr3X-hr4vGENvbl9qN9dGHv8IXjfeDdMLuqDr1alaYOHIPmQuPu1iJ-8_jBUOSUizIY4QFcxFhaMNgYl_GrTD30yw
```


## Key 생성하는 다른 명령어
