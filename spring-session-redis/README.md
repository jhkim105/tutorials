# Spring Session Data Redis

### Dependencies
```xml
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.session</groupId>
        <artifactId>spring-session-bom</artifactId>
        <version>2020.0.3</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.session</groupId>
      <artifactId>spring-session-data-redis</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```
### Configuration
application.yml
```yaml
spring:
#  session:
#    store-type: redis
  redis:
    host: localhost
    port: 6379

server:
  servlet:
    session:
      timeout: 1m
```
만료시간 지정 방법
- server.servlet.session.timeout
- spring.session.timeout
```yaml
spring:
  session:
    timeout: 2m
```



### Test

```java
public class SessionController {
  @GetMapping
  public Map get(HttpSession session) {
    log.debug("{}", session);
    session.getMaxInactiveInterval();
    Map<String, Object> result = new HashMap<>();
    result.put("sessionId", session.getId());
    result.put("maxInactiveInterval", session.getMaxInactiveInterval());
    result.put("lastAccessedTime", session.getLastAccessedTime());
    return result;
  }
```

```shell
curl http://localhost:8080/session
{"lastAccessedTime":1636028503825,"maxInactiveInterval":120,"sessionId":"bbee3145-0e0e-431b-8d8a-a134f9c89aea"}  
```

## Refs
* https://spring.io/projects/spring-session-data-redis
* https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html

