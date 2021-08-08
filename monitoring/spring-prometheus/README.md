Monitoring OpenSource - Prometheus
============================


## Spring Boot 설정
add Actuator and Micrometer
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

enable prometheus endpoint  
application.yml
```yml
spring:
  application:
    name: Prometheus Demo
management:
  endpoints:
    web:
      exposure:
        include: "prometheus"
  metrics:
    tags:
      application: ${spring.application.name}
```

## Prometheus
docker: https://prometheus.io/docs/prometheus/latest/installation/

prometheus.yml
```yml
global:
  scrape_interval: 10s # 10초 마다 Metric 을 Pulling
  evaluation_interval: 10s
scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus' # Application prometheus endpoint
    static_configs:
      - targets: ['host.docker.internal:8080'] # Application host:port
```

docker run
```shell
docker run --name prometheus -d \
-p 9090:9090 \
-v /Users/jihwankim/dev/docker/prometheus/data:/etc/prometheus \
prom/prometheus
```
http://localhost:9090/

## Grafana
https://grafana.com/docs/grafana/latest/installation/docker/  
docker run
```shell
docker run -d -p 3000:3000 --name grafana grafana/grafana
```
admin/admin

Add datasource and Create a dashboard

## Prometheus Java Client - TODO
https://github.com/prometheus/client_java


## References
https://meetup.toast.com/posts/237  
https://prometheus.io/docs/introduction/overview/
