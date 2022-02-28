Spring Cloud Zookeeper
==============================


## Setup

### Installing Apache Zookeeper
```shell
brew install zookeeper
brwe services start zookeeper
```
http://localhost:8080/commands

## Service Provider

```xml
  <properties>
    <java.version>11</java.version>
    <spring-cloud.version>2021.0.1</spring-cloud.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
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
@EnableDiscoveryClient
public class SpringCloudZookeeperProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudZookeeperProviderApplication.class, args);
  }

}
```

```yaml
spring:
  application:
    name: hello
  cloud:
    zookeeper:
      connect-string: localhost:2181
      discovery:
        enabled: true
server:
  port: 8081
```

## Service Consumer
```xml
  <properties>
    <java.version>11</java.version>
    <spring-cloud.version>2021.0.1</spring-cloud.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
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

```yaml
spring:
  application:
    name: consumer
  cloud:
    zookeeper:
      connect-string: localhost:2181
server:
  port: 8082
```

```java
@RestController
@RequiredArgsConstructor
public class HomeController {

  private final DiscoveryClient discoveryClient;


  private static final String SERVICE_NAME = "hello";

  @GetMapping("/")
  public String home() {
    ResponseEntity<String> response = new RestTemplate().getForEntity(serviceUrl(), String.class);
    return response.getBody();
  }

  private String serviceUrl() {
    List<ServiceInstance> list = discoveryClient.getInstances(SERVICE_NAME);
    if (list == null || list.isEmpty()) {
      throw new IllegalStateException("ServiceInstance not found.");
    }

    return list.get(0).getUri().toString();
  }
}

```




