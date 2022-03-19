AWS Lambda Example
===================

### Add dependency
```xml
<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-lambda-java-core -->
<dependency>
  <groupId>com.amazonaws</groupId>
  <artifactId>aws-lambda-java-core</artifactId>
  <version>1.2.1</version>
</dependency>
```

### Add maven-shade-plugin  
필요한 라이브러리를 포함하여 생성
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>3.2.4</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>shade</goal>
      </goals>
    </execution>
  </executions>
</plugin>

```

### Create Handler
3가지 방법 
- Custom method handler 작성
- RequestHandler Interface 구현
- RequestStreamHandler Interface 구현

2번째 방법 사용  

```java
public class LambdaRequestHandler implements RequestHandler<String, String> {

  @Override
  public String handleRequest(String input, Context context) {
    context.getLogger().log("Input:" + input);

    return "Hello Lambda - " + input;
  }

}
```


### JUnit Test
junit5 dependency
```xml
<!-- https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <version>5.7.2</version>
  <scope>test</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/org.junit.platform/junit-platform-runner -->
<dependency>
  <groupId>org.junit.platform</groupId>
  <artifactId>junit-platform-runner</artifactId>
  <version>1.7.2</version>
  <scope>test</scope>
</dependency>
```

TestContext.java
```java
public class TestContext implements Context {

    private String awsRequestId = "EXAMPLE";
    private ClientContext clientContext;
  ...

```

TestCase
```java
@Test
void test() {
  System.out.println("aaaa");

  Context ctx = new TestContext();

  LambdaRequestHandler handler = new LambdaRequestHandler();
  String output = handler.handleRequest("Test", ctx);
  Assertions.assertTrue(output.startsWith("Hello"));
}

```



