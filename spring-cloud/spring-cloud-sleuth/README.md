Spring Cloud Sleuth
===========================


## Configurations

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-sleuth</artifactId>
    </dependency>
```

```yaml
spring:
  application:
    name: Sleuth Example

```

## Simple Web Application And Log

```java
@RestController
@Slf4j
public class HomeController {

  @GetMapping("/")
  public String home() {
    log.info("Hello Sleuth");
    return "success";
  }

}
```

```shell
022-02-23 13:34:41.261  INFO [Sleuth Example,a925431bfc0b78aa,a925431bfc0b78aa] 27637 --- [nio-8080-exec-1] j.t.spring.cloud.sleuth.HomeController   : Hello Sleuth
```


## Async ( New Thread)
```java
  @GetMapping("/async")
  public String async() throws InterruptedException {
    log.debug("Before async method call");
    helloService.doSomethingAsync();
    log.debug("After async method call");
    return "success";
  }
```
traceId는 동일하고, spanId가 달라진다.
```shell
2022-02-23 15:36:29.194 DEBUG [Sleuth Example,e54f02b231bad84d,e54f02b231bad84d] 47966 --- [nio-8080-exec-1] j.t.spring.cloud.sleuth.HelloController  : Before async method call
2022-02-23 15:36:29.198 DEBUG [Sleuth Example,e54f02b231bad84d,e54f02b231bad84d] 47966 --- [nio-8080-exec-1] j.t.spring.cloud.sleuth.HelloController  : After async method call
2022-02-23 15:36:29.205 DEBUG [Sleuth Example,e54f02b231bad84d,e54f02b231bad84d] 47966 --- [nio-8080-exec-1] m.m.a.RequestResponseBodyMethodProcessor : Using 'text/html', given [text/html, application/xhtml+xml, image/avif, image/webp, image/apng, application/xml;q=0.9, application/signed-exchange;v=b3;q=0.9, */*;q=0.8] and supported [text/plain, */*, text/plain, */*, application/json, application/*+json, application/json, application/*+json]
2022-02-23 15:36:29.206 DEBUG [Sleuth Example,e54f02b231bad84d,e54f02b231bad84d] 47966 --- [nio-8080-exec-1] m.m.a.RequestResponseBodyMethodProcessor : Writing ["success"]
2022-02-23 15:36:29.206  INFO [Sleuth Example,e54f02b231bad84d,3922f219860211c3] 47966 --- [         task-1] j.t.spring.cloud.sleuth.HelloService     : Start
2022-02-23 15:36:29.211 DEBUG [Sleuth Example,e54f02b231bad84d,e54f02b231bad84d] 47966 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
2022-02-23 15:36:30.211  INFO [Sleuth Example,e54f02b231bad84d,3922f219860211c3] 47966 --- [         task-1] j.t.spring.cloud.sleuth.HelloService     : End

```



