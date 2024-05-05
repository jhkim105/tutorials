


### 쿼리 실행 횟수 검증하기
```xml
    <dependency>
      <groupId>io.hypersistence</groupId>
      <artifactId>hypersistence-utils-hibernate-63</artifactId>
      <version>3.7.5</version>
      <scope>test</scope>
    </dependency>
```

```java
@Component
public class DataSourceWrapper implements BeanPostProcessor {

  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof DataSource originalDataSource) {
      ChainListener listener = new ChainListener();
      SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
      loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
      listener.addListener(loggingListener);
      listener.addListener(new DataSourceQueryCountListener());
      return ProxyDataSourceBuilder
          .create(originalDataSource)
          .name("DataSourceProxy")
          .listener(listener)
          .build();
    }
    return bean;
  }

}
```

```java
  @Test
void findById() {
  SQLStatementCountValidator.reset();
  User user = userRepository.findById("id01").get();
  assertSelectCount(1);

}
```



## Refs
- https://vladmihalcea.com/how-to-detect-the-n-plus-one-query-problem-during-testing/
- https://github.com/vladmihalcea/hypersistence-utils/
