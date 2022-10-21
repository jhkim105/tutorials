WebClient Example
====================================


## WebClient Vs RestTemplate Comparison Exmaple

### Sample Service(UserController)
```java
@RestController
public class UserController {

  @GetMapping("/users")
  private List<User> getAllUsers() throws Exception {
    Thread.sleep(3000L);

    return Arrays.asList(
       new User("user01", "User 01"),
       new User("user02", "User 02")
    );
  }

}

```

### RestTemplate Blocking Client
```java
  @GetMapping("/users-blocking")
  public List<User> getUsersBlocking() {
    log.info("Blocking::START");
    String url = getUserServiceUrl();
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
        new ParameterizedTypeReference<>() {
        });
    List<User> result = responseEntity.getBody();
    log.info("Blocking::END");
    return result;
  }
```
실행결과
```
Blocking::START
User(username=user01, name=User 01)
User(username=user02, name=User 02)
Blocking::END
```

### WebClient Non-Blocking Client
```java
  @GetMapping(path = "/users-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<User> getUsersNonBlocking() {
    log.info("NonBlocking::START");
    Flux<User> userFlux = WebClient.create()
        .get()
        .uri(getUserServiceUrl())
        .retrieve()
        .bodyToFlux(User.class);
    log.info("NonBlocking::END");
    return userFlux;
  }

```

실행결과
```
NonBlocking::START
NonBlocking::END
User(username=user01, name=User 01)
User(username=user02, name=User 02)
```


## References
* https://www.baeldung.com/spring-webclient-resttemplate


