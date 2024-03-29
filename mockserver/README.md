#

## Dependencies

```xml
<dependency>
  <groupId>org.mock-server</groupId>
  <artifactId>mockserver-junit-jupiter-no-dependencies</artifactId>
  <version>5.14.0</version>
</dependency>
```
## MockServer And JUnit5
```java
@SpringBootTest
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8888})
@RequiredArgsConstructor
@Slf4j
class MockServerTests {

  private final ClientAndServer mockServer;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void test() throws Exception {
    Map<String, Object> resMap = new HashMap<>();
    resMap.put("code", "100");
    resMap.put("message", "return message");

    int delaySeconds = 3;
    mockServer
        .when(
            request()
                .withMethod("POST")
                .withPath("/mock_post")
        )
        .respond(
            response()
                .withBody(objectMapper.writeValueAsString(resMap))
                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                .withDelay(TimeUnit.SECONDS, delaySeconds)
        );

    WebClient webClient = WebClient.create("http://localhost:8888");

    Mono<String> responseMono = webClient.post()
        .uri("/mock_post")
        .contentType(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(String.class);

    String expected = objectMapper.writeValueAsString(resMap);
    StepVerifier.create(responseMono)
        .expectSubscription()
        .expectNoEvent(Duration.ofSeconds(delaySeconds))
        .expectNextMatches(response -> {
          log.info("response: {}", response);
          return response.equals(expected);})
        .verifyComplete();

  }

}
```


## References
* https://www.mock-server.com/mock_server/running_mock_server.html
