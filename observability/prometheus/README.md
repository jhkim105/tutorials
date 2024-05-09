

## Docker
### Configuration
https://prometheus.io/docs/prometheus/latest/configuration/configuration/  
prometheus.yml
```shell
global:
  scrape_interval: 10s # 10초 마다 Metric 을 Pulling
  evaluation_interval: 10s
scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus' # Application prometheus endpoint
    static_configs:
      - targets: ['host.docker.internal:8080'] # Application host:port
```
```shell
docker run --rm \
    -p 9090:9090 \
    -v ./prometheus.yml:/etc/prometheus/prometheus.yml \
    -v prometheus-data:/prometheus \
    prom/prometheus
```

### 접속
http://localhost:9090/



## Prometheus Tip

### Reload Config
```shell
curl -X POST http://127.0.0.1:9090/-/reload
```
Lifecycle API is not enabled 에러나면서 안될 경우에 아래 옵션 필요
--web.enable-lifecycle


## Refs
- https://prometheus.io/docs/prometheus/latest/getting_started/