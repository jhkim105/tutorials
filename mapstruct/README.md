

## Setup

```xml

    <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>${mapstruct.version}</version>
    </dependency>
```
```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <annotationProcessorPaths>
            <path>
              <groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId>
              <version>${mapstruct.version}</version>
            </path>
          </annotationProcessorPaths>
        </configuration>
    </plugin>
```

## Basic Mapping

### Mapper Interface

Mapper Factory 사용
```java
public interface SimpleMapperWithInstance {

  SimpleMapperWithInstance INSTANCE = Mappers.getMapper(SimpleMapperWithInstance.class);

  SimpleDestination sourceToDestination(SimpleSource source);

}

```

DI 사용
```java
@Mapper(componentModel = "spring")
public interface SimpleMapper {

  SimpleDestination sourceToDestination(SimpleSource source);

  SimpleSource destinationToSource(SimpleDestination destination);

}
```
Mapper 에 Spring bean 주입하기
```java
  @Mapping(target = "name", expression = "java(simpleService.uppercase(source.name()))")
  public abstract SimpleDestination sourceToDestination(SimpleSource source);
```



## Refs
- https://www.baeldung.com/mapstruct






