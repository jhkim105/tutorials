JWT
===========================

## JWT (JSON Web Token)
URL-safe, base64 URL-encoded, cryptographically signed(OR encrypted) Token
Header(json) + Payload(json) + Signature 로 구성
구현 방식에 따라서 JWS(Json Web Signature) OR JWE(Json Web Encryption)
일반적으로 JWT는 JWS를 말함

### The Header
Base64 encoded JSON String  
SHA-256 을 사용한 HMAC 으로 서명되었음을 표시함
```json
{"alg":"HS256"}
```

### The Payload
#### Claims
Registered Claims

|Name|Description    |
|:---|:---|
|iss|Issuer|
|sub|Subject|
|aud|Audience|
|exp|Expiration|
|nbf|Not Before|
|iat|Issued At|
|jti|JWT ID|

약속된 claim 외에 추가로 Custom claim 을 사용할 수 있다. 

### The Signature
HMAC 을 사용하여 Signature 를 생성함 
sign(Header + Payload, Private Key)

## Library - jjwt
https://github.com/jwtk/jjwt

### Maven
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
```
### Sample Code
```java
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
    log.info("{}", jws);
    assertThat(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject())
        .isEqualTo("Joe");

    String secretString = Encoders.BASE64.encode(key.getEncoded());
    log.info("{}", secretString);
```

### Signature Algorithms
- HS256: HMAC using SHA-256
- HS384: HMAC using SHA-384
- HS512: HMAC using SHA-512
- ES256: ECDSA using P-256 and SHA-256
- ES384: ECDSA using P-384 and SHA-384
- ES512: ECDSA using P-521 and SHA-512
- RS256: RSASSA-PKCS-v1_5 using SHA-256
- RS384: RSASSA-PKCS-v1_5 using SHA-384
- RS512: RSASSA-PKCS-v1_5 using SHA-512
- PS256: RSASSA-PSS using SHA-256 and MGF1 with SHA-256
- PS384: RSASSA-PSS using SHA-384 and MGF1 with SHA-384
- PS512: RSASSA-PSS using SHA-512 and MGF1 with SHA-512

io.jsonwebtoken.SignatureAlgorithm enum 에 정의되어 있음

#### HMAC-SHA
HS256, HS384, HS512  
최소 key lengths:
- HS256: 256 bit (32 bytes)
- HS384: 384 bit
- HS512: 512 bit

#### RSA
RS256, RS384, RS512, PS256, PS384, PS512  
권장 최소 key lengths:
- RS256, PS256: 2048 bit
- RS256, PS256: 3072 bit
- RS512, PS512: 4096 bit

jjwt 는 key length 가 2048 이상인지만 체크함

#### Elliptic Curve
ES256, ES384, ES512
최소 key lengths:  
- ES256: 256 bits (32 bytes)
- ES384: 384 bits (48 bytes)
- ES512: 521 bits (65 or 66 bytes)




## Library - java-jwt
https://github.com/auth0/java-jwt  








