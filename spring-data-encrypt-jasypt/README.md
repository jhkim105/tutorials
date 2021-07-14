DB 암호화 with Jasypt
=========================


### dependency

```xml
<!-- https://mvnrepository.com/artifact/org.jasypt/jasypt-hibernate5 -->
<dependency>
    <groupId>org.jasypt</groupId>
    <artifactId>jasypt-hibernate5</artifactId>
    <version>1.9.3</version>
</dependency>
```

### JasyptConfig
```java
@Configuration
public class JasyptConfig {


  @Bean
  public PooledPBEStringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor stringEncryptor = new PooledPBEStringEncryptor();
    stringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    stringEncryptor.setPassword(("secret#01"));
    stringEncryptor.setPoolSize(4);
    return stringEncryptor;
  }

  @Bean
  public HibernatePBEStringEncryptor hibernateStringEncryptor(PooledPBEStringEncryptor stringEncryptor) {
    HibernatePBEStringEncryptor hibernateStringEncryptor = new HibernatePBEStringEncryptor();
    hibernateStringEncryptor.setRegisteredName("hibernateStringEncryptor");
    hibernateStringEncryptor.setEncryptor(stringEncryptor);
    return hibernateStringEncryptor;
  }

}
```

### hibernate
package-info.java
```java
@TypeDef(
    name="encryptedString",
    typeClass= EncryptedStringType.class,
    parameters= {
        @Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
    }
)
```

Entity
```java
  @Column(nullable = false)
  @Type(type="encryptedString")
  private String username;
```

검색조건에 사용할 경우 직접 복호화해야 한다. Spring Data method로는 조회가 안된다.  
  - User user = repository.findByUsername("user01");


저장된 데이터가 평문일 경우 Exception 발생
평문일 경우에 예외 밝생하지 않고 평문이 조회되도록 Custom Class 작성.
기존 시스템 데이터 변경없이, 암호화를 추가하는 경우에 필요하다.
```java
@Slf4j
public class CustomPBStringEncryptor implements PBEStringCleanablePasswordEncryptor {

  private final PooledPBEStringEncryptor pooledPBEStringEncryptor;

  public CustomPBStringEncryptor(PooledPBEStringEncryptor pooledPBEStringEncryptor) {
    this.pooledPBEStringEncryptor = pooledPBEStringEncryptor;
  }


  @Override
  public String encrypt(String message) {
    return pooledPBEStringEncryptor.encrypt(message);
  }

  @Override
  public String decrypt(String encryptedMessage) {
    String ret;
    try {
      ret = pooledPBEStringEncryptor.decrypt(encryptedMessage);
    } catch(Exception ex) {
      //ignored
      log.debug("{}", ex.toString());
      ret = encryptedMessage;
    }
    return ret;
  }

  @Override
  public void setPasswordCharArray(char[] password) {
    pooledPBEStringEncryptor.setPasswordCharArray(password);
  }

  @Override
  public void setPassword(String password) {
    pooledPBEStringEncryptor.setPassword(password);
  }
}

```

### Encrypt password(secret) 숨기기
자바 또는 시스템 환경변수 사용
- System.getProperty("jasypt.encryptor.password")
- System.getenv("jasypt.encryptor.password")


## References
http://www.jasypt.org/hibernate.html

