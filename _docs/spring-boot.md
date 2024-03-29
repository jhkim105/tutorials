Spring Boot
==================

## Install
xxx.conf
```
JAVA_OPTS="-Xms2g -Xmx2g"
JAVA_OPTS="$JAVA_OPTS -Xloggc:./logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
#RUN_ARGS="--server.port=8080 --server.address=0.0.0.0"
JAVA_OPTS="$JAVA_OPTS -Dlogging.config=logback-spring.xml"
LOG_FOLDER="/DATA/Logs/tomcat"
RUN_AS_USER="tomcat"
```
config/application.properties
```
```

logback-spring.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="5 seconds">
  <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
  <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>
  <logger name="org.springframework.boot" level="INFO"/>
  <logger name="com.rsupport" level="INFO"/>
  <root level="WARN">
    <appender-ref ref="CONSOLE"/>
  </root>
</configuration>
```

Service 등록 및 실행
```
sudo chown u+x xxx.jar
sudo ln -s /DATA/WEB/xxx.jar /etc/init.d/xxx
sudo chkconfig xxx on
sudo service xxx start
sudo service xxx stop
service xxx start
service xxx stop
```

### systemd
/etc/systemd/system/web.service
```
[Unit]
Description=rm web
After=syslog.target

[Service]
Type=simple
WorkingDirectory=/DATA/WEB/remotemeeting
ExecStart=/bin/sh -c "/DATA/WEB/remotemeeting/web.war >> /DATA/Logs/tomcat/web.log"
`
SuccessExitStatus=143
Restart=on-failure
RestartSec=5

User=webdev1
Group=webdev1
UMask=0027

# Logging
#SyslogIdentifier=web

LimitNOFILE=131072
LimitNPROC=131072

[Install]
WantedBy=multi-user.target
```
LOG_FOLDER 설정이 systemd 서비스에는 동작하지 않는다.
로그 확인은 systemd 로그 확인 명령인 journalctl 을 사용해야 한다.
```
journalctl -f -u web
```

== ApplicationRunner and CommandLineRunner
애플리케이션 구동 시점에 특정 코드 실행하기

ApplicationRunner
```java
@Component
@Slf4j
public class CustomApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
    
    }
}
```

CommandLineRunner
```java
@Component
public class CustomCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
    
    }

}
```

@Bean 으로 등록
```java
  @Bean
  public ApplicationRunner loggingProperties(AppProperties appProperties) {
    return (args) -> {
      log.info("{}", appProperties);
    };

  }
```
