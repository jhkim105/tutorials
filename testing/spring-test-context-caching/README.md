

## Context Caching
TestContext framework 가 ApplicationContext 를 Caching 하여 재사용한다.  
다음과 같은 경우에는 재사용하지 않고 새로 생성한다.
- Mocking
- @ActiveProfile
- @TestPropertySource
- @ContextConfiguration
- @DirtiesContext

### Mock 개선
Mock 을 사용하려면 최대한 Context 범위를 좁혀 사용한다. (가급적 @SpringBootTest 와 사용하지 않기)  
Mock Instance 를 공유해서 사용한다. 이렇게 하면 동일한 Mock 객체를 사용하는 테스트에서 Context 를 재사용한다.  
MockConfig
```java
@TestConfiguration
public class MockConfig {

  @Primary
  @Bean
  GreetingService createGreetingServiceMock() {
    return mock(
        GreetingService.class,
        MockReset.withSettings(MockReset.AFTER)
    );
  }
}
```

Test
```java
@WebMvcTest
@ContextConfiguration(classes = MockConfig.class)
@AutoConfigureMockMvc
class MockConfig1Test {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private GreetingService greetingService;
```


### Context Configuration
logging
```properties
logging.level.org.springframework.test.context.cache=DEBUG
```
cache size
```properties
spring.test.context.cache.maxSize=50
```




## References
https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/ctx-management/caching.html

