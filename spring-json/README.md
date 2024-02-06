

## Customize Default Object Mapper

### Application Properties and Custom Jackson Module
application.properties
```properties
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.default-property-inclusion=always
```
date format 지정하기
```java
  public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:SSS";

  @Bean
  public JavaTimeModule javaTimeModule() {
    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
    return module;
  }
```


### Jackson2ObjectMapperBuilderCustomizer

```java
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder.serializationInclusion(JsonInclude.Include.NON_NULL)
        .serializers(LOCAL_DATETIME_SERIALIZER);
  }
```


## Overriding the Default Configuration

### ObjectMapper
```java
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(LOCAL_DATETIME_SERIALIZER);
    return new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .registerModule(module);
  }
```


