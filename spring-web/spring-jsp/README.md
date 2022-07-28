Spring Boot Jsp Example
========================



## Custom Error Page
WEB-INF/views/error.jsp 를 추가하면 해당 페이지를 에러페이지로 사용한다. 추가로 Customize 가 필요한 경우 ErrorController 를 구현해주면 된다.



## Issues

### JSP Slow 이슈
spring boot 2.3 에서  jsp page 호출시 속도가 느림. 최초 요청이 느리고  1~2 초 이내로 부르면 빠르게 응답했다가 느리게 응답하는것을 반복함
https://github.com/spring-projects/spring-boot/issues/24744
이 소스에서는 재현안됨.
느릴 경우 다음 설정 추가
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-tomcat</artifactId>
  <scope>provided</scope>
  <exclusions>
    <exclusion>
      <groupId>org.glassfish</groupId>
      <artifactId>jakarta.el</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```
4초 간격으로 페이지 응답이 느려지는 현상
jasper 설정 중 development 속성 비활성화. 이 옵션이 활성화 되어 있을 경우 4초(modificationTestInterval) 마다 jsp 수정 여부를 체크함
jsp live reloading을 위해서 아래 프로퍼티를 활성화 하는데 운영중에는 이 옵션을 비활성화 해야 한다.
```
server.servlet.jsp.init-parameters.development=true
```

### IntelliJ 에서 실행시 404 이슈
IntelliJ 에서 Application 실행시 jsp 찾지 못하는 404 에러가 발생한다. 다음 설정을 추가해야 한다.  
Run Configuration >Environments  
Working Directory: $MODULE_WORKING_DIR$ 







