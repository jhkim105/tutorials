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

## References
http://www.jasypt.org/hibernate.html

