DB 암호화 하기
==========================

## DB 에서 제공하는 암복호화 함수를 이용하여 암호화

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

저장된 데이터가 평문일 경우 null 로 조회된다.
DB에 저장된 암호화된 값을 복호화 화여 조회 조건절에 사용하므로 like 검색도 가능하지만, index 를 태울 수가 없다. 
```text
Hibernate: select user0_.id as id1_0_, user0_.description as descript2_0_, cast(AES_DECRYPT(UNHEX(user0_.name), 'secret1111') as CHAR) as name3_0_, user0_.username as username4_0_ from user user0_ where cast(AES_DECRYPT(UNHEX(user0_.name), 'secret1111') as CHAR) like ? escape ?
2024-02-02 11:15:46.924 TRACE 94341 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [VARCHAR] - [Name%]
2024-02-02 11:15:46.924 TRACE 94341 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [CHAR] - [\]

```
### @Converter 

StringEncryptConverter.java
```java
public class StringEncryptConverter implements AttributeConverter<String, String> {


  private static final String P = "1234567890123456";

  @Override
  public String convertToDatabaseColumn(String s) {
    return Aes.getInstance(P).encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    return Aes.getInstance(P).decrypt(s);
  }
}

```

User.java
```java
  @Column
  @Convert(converter = StringEncryptConverter.class)
  private String phoneNumber;
```
암호화 값으로 where 절에 사용하므로 index 는 태울 수 있으나, like 검색을 할 수 없다.
```text
Hibernate: select user0_.id as id1_0_, user0_.description as descript2_0_, cast(AES_DECRYPT(UNHEX(user0_.name), 'secret1111') as CHAR) as name3_0_, user0_.username as username4_0_ from user user0_ where user0_.description like ? escape ?
2024-02-02 11:11:45.622 TRACE 93411 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [1] as [CLOB] - [bPErQQ/6czOex+TMHLau655984WmaQ5W]
2024-02-02 11:11:45.622 TRACE 93411 --- [           main] o.h.type.descriptor.sql.BasicBinder      : binding parameter [2] as [CHAR] - [\]

```