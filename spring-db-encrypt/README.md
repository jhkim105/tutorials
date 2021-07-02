DB 암호화 하기
==========================

DB에서 제공하는 암복호화 함수를 이용하여 암호화

### MySQL
```sql
select HEX(AES_ENCRYPT('original', 'secret'));
select cast(AES_DECRYPT(UNHEX('CC743094E3FC0AE970F96476C131AADF'), 'secret') as CHAR);

```

### @ColumnTransformer
도메인 객체에 설정
```java
  @Column(nullable = false)
  @ColumnTransformer(
      read = "cast(AES_DECRYPT(UNHEX(username), 'secret') as CHAR)",
      write = "HEX(AES_ENCRYPT(?, 'secret'))")
  private String username;
```

### 암복호화키 코드에서 감추려면
키를 리턴하는 MySQL function 작성

```mysql
CREATE FUNCTION fn_enckey()
    RETURNS VARCHAR(50)

BEGIN
    RETURN 'secret#01';
END
```

```java
  @Column(nullable = false)
  @ColumnTransformer(
      read = "cast(AES_DECRYPT(UNHEX(username), fn_enckey()) as CHAR)",
      write = "HEX(AES_ENCRYPT(?, fn_enckey()))")
  private String username;
```

저장된 데이터가 평문일 경우 null로 조회된다.