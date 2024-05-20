

## SetUp
```xml
    <dependency>
      <groupId>me.desair.tus</groupId>
      <artifactId>tus-java-server</artifactId>
      <version>1.0.0-3.0</version>
    </dependency>
```

TusConfig
```java
@Configuration
public class TusConfig {

  @Value("#{servletContext.contextPath}")
  private String contextPath;

  @Value("${tus.storage-path}")
  protected String tusStoragePath;

  @Bean
  public TusFileUploadService tusFileUploadService() {
    return new TusFileUploadService()
        .withStoragePath(tusStoragePath)
        .withDownloadFeature()
        .withUploadUri(contextPath + "/tus")
        .withThreadLocalCache(true);

  }

}
```
## Controller
upload/download/delete 를 모두 처리함
```java
  @RequestMapping(value = {"", "/**"},
      method = {POST, PATCH, HEAD, DELETE, OPTIONS, GET})
  public void process(final HttpServletRequest request, final HttpServletResponse response) {
    try {
      uploadService.process(request, response);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
```




## Refs
- https://tus.io/implementations