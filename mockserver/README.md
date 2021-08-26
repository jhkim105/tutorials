#

## WorkLog
아래 dependency는 import가 안됨

```java
		<dependency>
			<groupId>org.mock-server</groupId>
			<artifactId>mockserver-spring-test-listener</artifactId>
			<version>5.11.1</version>
		</dependency> 

```

자바 버전이 올라가서 그런지 테스트 케이스 에러남. disabled 처리
mockserver-junit-jupiter
```
java.lang.IllegalAccessException: class io.netty.util.internal.PlatformDependent0$6 cannot access class jdk.internal.misc.Unsafe (in module java.base) because module java.base does not export jdk.internal.misc to unnamed module @769a1df5\n"
    + "\tat java.base/jdk.internal.reflect.Reflection.newIllegalAccessException(Reflection.java:361)
```


## References
* https://www.mock-server.com/mock_server/running_mock_server.html
* https://www.baeldung.com/mockserver
