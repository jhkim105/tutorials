## DB 초기화 
* Schema 반영
* 초기데이터 적재

현재 사용하고 있는 DB 초기화는 다음과 같은 방법이 있다.
### 방법 1 - maven plugin 활용
#### Schema 반영
* hibernate-maven-plugin을 통해 schema sql 생성(create.sql)
* sql-maven-plugin을 통해 create.sql 실행
####  초기데이터 적재
* Excel을 Xml로 변환
* dbunit-maven-plugin을 통해서 초기데이터 적재

### 방법 2 - hbm2ddl.auto option
### Schema 반영
* spring.jpa.hibernate.ddl-auto=create

### 초기데이터 적재
* spring.sql.init.mode=always 옵션 사용
  
### 문제점
* 테스트 케이스(클래스) 마다 초기화 되어 비효율적이다.
* 웹서버 시작시에 초기화한다. 실수로 운영서버 적용될 위험이 있다. 방법 1처럼, build시에만 초기화 되도록 하는 것이 좋겠다.

```properties
spring.sql.init.mode=always
#spring.sql.init.data-locations=classpath*:data.sql
#spring.sql.init.continue-on-error=true
```

서버 반영시는 Flyway를 활용.

### 개선된 방법
maven plugin 을 사용하지 않고, DB 초기화하는 효율적인 방법(방법 2 단점(테스트 케이스마다 초기화 되는 문제))을 찾아보자
* application 설정은 DB 초기화 비활성화
* src/main/resources/application.properties
    ```properties
    spring.jpa.hibernate.ddl-auto=validate
    spring.sql.init.mode=never
    ```  
* DB 초기화 프로퍼티 추가 및 테스트 케이스 작성
* src/test/resources/application-test.properties
    ```properties
    spring.jpa.defer-datasource-initialization=true
    spring.jpa.hibernate.ddl-auto=create
    spring.sql.init.mode=always
    ```  
* TestCase
  ```java
  @FirstRun
  @SpringBootTest
  @TestPropertySource(locations = {"classpath:/application.properties", "classpath:/application-test.properties"})
  @Slf4j
  public class DBInitializeTest {
  
    @Test
    void runFirst() {
      log.info("First Run.");
    }
  }
  
  ```
* 위 테스트 케이스가 제일 먼저 실행되도록 하자. 실행순서를 지정하는 것은 junit-jupiter 2.8.0-M1 버전에서 가능함
  ```xml
    <junit-jupiter.version>5.8.0-M1</junit-jupiter.version>
  ```

  실행순서 지정하기 위해서 ClassOrder 구현체 작성
  ```java
  public class TestClassOrderer implements ClassOrderer {
      @Override
      public void orderClasses(ClassOrdererContext classOrdererContext) {
          classOrdererContext.getClassDescriptors().sort(Comparator.comparingInt(TestClassOrderer::getOrder));
      }
  
      private static int getOrder(ClassDescriptor classDescriptor) {
          if (classDescriptor.findAnnotation(FirstRun.class).isPresent()) {
              return 0;
          } else {
              return 1;
          }
      }
  }
  ```

  src/test/resources/junit-platform.properties 추가
  ```properties
  junit.jupiter.testclass.order.default=com.example.demo.TestClassOrderer
  ```
