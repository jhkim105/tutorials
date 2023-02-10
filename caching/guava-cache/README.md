Guava Cache
=====================

## Configuration

```xml
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>31.1-jre</version> 
    </dependency>
```

만료된 캐시는 자동으로 삭제되지 않음. 해당 캐시를 접근하거나, maximum size 에 도달했을때 삭제 됨.  
https://github.com/google/guava/wiki/CachesExplained#when-does-cleanup-happen  
주기적으로 캐시를 정리하고 싶으면 주기적(ScheduledExecutorService)으로 Cache.cleanup() 을 호출해야 한다.

## Refs
https://github.com/google/guava/
https://www.baeldung.com/guava-cache
