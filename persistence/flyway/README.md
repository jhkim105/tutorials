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

### SQL for Migration
resources/db/migration/V0_0_1.init.sql


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








