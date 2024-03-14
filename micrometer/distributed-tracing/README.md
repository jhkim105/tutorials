# Distributed Tracing
- 분산환경에서 로깅
- Spring Cloud Sleuth 에서 Micrometer Tracing 으로 이동함

## Setup
- Observability > Distributed Tracing
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
```
- Configuration
```properties
management.tracing.sampling.probability=1.0
```


## References
[Migration-Guide](https://github.com/micrometer-metrics/tracing/wiki/Spring-Cloud-Sleuth-3.1-Migration-Guide)
[Micrometer Sample ](https://github.com/micrometer-metrics/micrometer-samples/)