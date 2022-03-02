Spring Cloud Vault
===========================

## Install Vault on Mac
```shell
brew install hashicorp/tap/vault
brew upgrade hashicorp/tap/vault
```

Run
개발환경으로 구동하기. 실서비스에서는 사용하면 안된다.
```shell
vault server -dev
```


root token으로 로그인
http://localhost:8200


## Spring Cloud Vault

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-vault-config</artifactId>
</dependency>
```

Create secret
```shell
vault kv put secret/spring-cloud-vault mykey=myvalue
```


application.yml
```yaml
spring:
  application:
    name: spring-cloud-vault
  cloud:
    vault:
      uri: http://localhost:8200
      authentication: TOKEN
      token: s.DMTWSKGCeozufCAVp2B1uc0x
  config:
    import: vault://
```

```java
@SpringBootApplication
@Slf4j
public class SpringCloudVaultApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudVaultApplication.class, args);
  }

  @Value("${mykey}")
  String mykey;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("mykey:{}", mykey);
  }
}

```

