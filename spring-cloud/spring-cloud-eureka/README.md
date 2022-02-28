Spring Cloud Netflix Eureka
====================================

## Eureka Server

```xml
  <properties>
    <java.version>11</java.version>
    <spring-cloud.version>2021.0.1</spring-cloud.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
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

```java
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEurekaServerApplication.class, args);
  }

}
```

```yaml
server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

## Eureka Client

```xml
  <properties>
    <java.version>11</java.version>
    <spring-cloud.version>2021.0.1</spring-cloud.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
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

```java
@SpringBootApplication
public class SpringCloudEurekaClientApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCloudEurekaClientApplication.class, args);
  }
}
```

```yaml
server:
  port: 0
spring:
  application:
    name: spring-cloud-eureka-client
eureka:
  client:
    service-url:
      defaultZone: ${EUREKA_URI:http://localhost:8761/eureka}
  instance:
    prefer-ip-address: true
```

http://localhost:8761

## EurekaClient 

```java
@RestController
public class HomeController {

  private static final String SERVICE_NAME = "spring-cloud-eureka-client";

  @Lazy
  @Autowired
  private EurekaClient eurekaClient;

  @GetMapping("/")
  public String home() {
    InstanceInfo instance = eurekaClient.getApplication(SERVICE_NAME).getInstances().get(0);
    String hostName = instance.getHostName();

    URI uri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host(hostName)
        .path("")
        .port(instance.getPort())
        .build().toUri();

    ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);
    return response.getBody();
  }


}
```

