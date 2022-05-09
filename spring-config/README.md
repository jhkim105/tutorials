

##

### spring.config.location
지정한 파일만 로드함
```shell
java -jar spring-config-0.0.1-SNAPSHOT.jar --spring.config.location=classpath:/application.yml,file:/Users/jihwankim/dev/my/tutorials/spring-config/a.yml
```


###spring.config.additional-location
지정한 파일로 오버라이드 함
```shell
java -jar spring-config-0.0.1-SNAPSHOT.jar --spring.config.additional-location=file:/Users/jihwankim/dev/my/tutorials/spring-config/a.yml
```



