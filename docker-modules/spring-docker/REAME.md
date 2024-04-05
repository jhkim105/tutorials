

## Basic Dockerfile

### Dockerfile
```text
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Build

```text
docker build -t jhkim105/myapp .
```

Mac M1 에서는 다음 에러 발생. arm64 image 가 없어서 발생
```text
failed to solve with frontend dockerfile.v0: failed to create LLB definition: no match for platform in manifest sha256:9909002ad26c12ac3be05d258f6424cd25620042ab682358a5dfbfe866885846: not found

```
platform 을 지정
```text
# platform 지정
docker build -t jhkim105/myapp . --platform linux/x86_64
```
또는 arm64 를 지원하는 베이스 이미지로 변경. amazoncorretto:17 는 지원함
```text
FROM amazoncorretto:17
```

### Run
```text
docker run -p 8080:8080 jhkim105/myapp
```



## Refs
- [Spring - Getting Started](https://spring.io/guides/topicals/spring-boot-docker)
- [Spring - Container Images](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images)
- [Baeldung- Creating Docker Images with Spring Boot](https://www.baeldung.com/spring-boot-docker-images)
- [Docker Build](https://docs.docker.com/build/)
