Property Encryption Using Jasypt
============

## Maven Dependencies
```xml
    <dependency>
      <groupId>com.github.ulisesbocchio</groupId>
      <artifactId>jasypt-spring-boot-starter</artifactId>
      <version>3.0.4</version>
    </dependency>
    <dependency>
      <groupId>com.github.ulisesbocchio</groupId>
      <artifactId>jasypt-spring-boot</artifactId>
      <version>3.0.4</version>
    </dependency>
```

## Configuration

application.yml
```yaml
jasypt:
  encryptor:
    password: secret1234
    
app:
  password: ENC(VCr9a+qfrOy50aheLoseCKmZ/YeDZzMBQwk7UPUoUr6PISgiW+VXvF+a6yBB2vzn)
```
Password 는 System Property 로 지정 가능(-Djasypt.encryptor.password=secret1234)

## Maven Plugin
jasypt-maven-plugin 을 사용하여 암/복호화 하기 
```xml
<build>
  <plugins>
    <plugin>
      <groupId>com.github.ulisesbocchio</groupId>
      <artifactId>jasypt-maven-plugin</artifactId>
      <version>3.0.4</version>
    </plugin>
  </plugins>
</build>
```
### Encryption
```shell
 mvn jasypt:encrypt-value -Djasypt.encryptor.password="secret1234" -Djasypt.plugin.value="original-password"
```

### Decryption
```shell
mvn jasypt:decrypt-value -Djasypt.encryptor.password="secret1234" -Djasypt.plugin.value="ef0QM5m0w8R9HaQ4y4RqGIAIphZNXwyDwBWm165UdkiC7U3vRJzPbCoEzhD+SnknukrLzsqSzLWXvj5O77fVXQ=="
```

### Encryption property file
실행 전
```yaml
app:
  password: DEC(1234)
```
```shell
mvn jasypt:encrypt -Djasypt.encryptor.password="secret1234" -Djasypt.plugin.path="file:src/main/resources/application.yml"
```

실행결과: 파일이 변경됨
```yaml
app:
  password: ENC(yPLSbwn2IBcLYHNRvQyabUPdJh92QuefDbWobfkGON2gHlOADKdmFgen9t3Nnkj8)
```
### Decryption property file

```shell
mvn jasypt:decrypt -Djasypt.encryptor.password="secret1234" -Djasypt.plugin.path="file:src/main/resources/application.yml"
```
실행결과: 콘솔에 출력됨
```shell
#jasypt:
#  encryptor:
#    bean: stringEncryptor

app:
  password: DEC(1234)

```
이 플러그인을 사용하는 경우 기본 설정으로만 사용 가능하고 알고리즘이나 encryptor bean 을 지정하여 사용할 수 없다.

기본값으로 작성한 StringEncryptor
```java
  @Bean
  public StringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    config.setPassword("secret1234"); //System.setProperty("jasypt.encryptor.password", "secret1234")
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    return encryptor;
  }
```

## Refs
https://github.com/ulisesbocchio/jasypt-spring-boot

