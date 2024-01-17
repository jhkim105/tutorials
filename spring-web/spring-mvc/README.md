# spring-mvc
Spring MVC Sample Code



## Context Path, Servlet Path
server.servlet.context-path
- application root context path 를 지정
- 전체 어플리케이션의 기본 경로를 지정

spring.mvc.servlet.path
- DispatcherServlet path 를 지정 (Spring MVC 경로 지정)

http://localhost:8080/{context-path}/{servlet-path}

```
server.servlet.context-path=/web
spring.mvc.servlet.path=/mvc
```
application 은 /web 경로를 서비스하고 /mvc 는 Spring MVC(DispatcherServlet) 가 처리한다.
http://localhost:8080/web/mvc
위 설정에서 추가로 서블릿을 등록하게 되면 /web/xxx 경로로 매핑하게 된다.
## Refs


