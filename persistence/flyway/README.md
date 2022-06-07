Flyway
======================


## Spring Boot Application 에 적용하기

### Maven
```xml
`    <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-core</artifactId>
    </dependency>`
```

### Properties
```yaml
spring:
  datasource:
    url: jdbc:mariadb://localhost/demo_flyway_real?createDatabaseIfNotExist=true
    username: root
    password: 111111
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    enabled: true
```

### Script for Migration
resources/db/migration/V0_0_1.init.sql  
Script 작성시 ddl은 한파일에 하니씩 작성하는 것이 좋다. (mysql 은 ddl rollback 처리가 되지 않으므로 에러 발생시 조치하기 쉽게)  
ddl 과 dml 은 구분해서 작성하는 것이 좋다.

## Callbacks
migration 이 실패할 경우 실패한 이력을 자동 삭제 처리

db/callback/afterMigrateError__repair.sql
```sql
DELETE FROM flyway_schema_history WHERE success=false;
```

```properties
spring.flyway.locations=classpath:db/migration,classpath:db/callback
```

## Baseline
기 존재하는 Database 에 migration 하는 경우
```properties
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=0
```
spring.flyway.baseline-version 보다 버전이 큰 파일들만 실행된다.

## Flyway Using command-line

https://flywaydb.org/documentation/usage/commandline/
```shell
brew install flyway
```

flyway.conf
```properties
flyway.url=jdbc:mariadb://localhost/demo_flyway_real?createDatabaseIfNotExist=true
flyway.user=root
flyway.password=111111
```

sql/V0_0_1__schema.sql

```shell
flyway migrate
flyway info
flyway repair
flyway validate 
```

