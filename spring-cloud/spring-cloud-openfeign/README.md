Spring Cloud OpenFeign
=================================



## Dependencies
```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
```
```xml
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

## Feign Clients
```java

@SpringBootApplication
@EnableFeignClients
public class SpringCloudOpenfeignApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudOpenfeignApplication.class, args);
  }

}

```

```java
@FeignClient(value = "json-place-holder-client", url = "https://jsonplaceholder.typicode.com/")
public interface JsonPlaceHolderClient {

  @RequestMapping(method = RequestMethod.GET, value = "/posts")
  List<Post> getPosts();

  @RequestMapping(method = RequestMethod.GET, value = "/posts/{id}")
  Post getPost(@PathVariable String id);
}
```

## Logging
```java
  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.BASIC;
  }
```
application logging level 이 debug 일 경우에만 동작한다.
````yaml
logging:
  level:
    "jhkim105.tutorials": debug
````



## Refs
https://www.baeldung.com/spring-cloud-openfeign

