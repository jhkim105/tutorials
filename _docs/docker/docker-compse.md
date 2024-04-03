
## Command
```
docker compose up -d
docker compose ps
docker compose run web
docker compose down
docker compose start
docker compose stop
docker compose config
```
## Custom network
```yaml
version: '3.9'

networks:
  my-custom-network:
    driver: bridge
    ipam:
      driver: default
      config:
        - subnet: 172.20.0.0/16

services:
  webapp:
    image: nginx
    networks:
      - my-custom-network
    ports:
      - "8080:80"

```
또는 default 로 생성하면 지정하지 않아도 됨
```yaml
version: '3.9'

networks:
  default:
    driver: bridge
    ipam:
      driver: default
      config:
        - subnet: 172.20.0.0/16

services:
  webapp:
    image: nginx
    ports:
      - "8080:80"

```


## 초기 파일 공유하기
Dockerfile
```text
FROM alpine
WORKDIR data
COPY apns apns
COPY controlModule controlModule
COPY defaultTheme defaultTheme
COPY geoip geoip
COPY info.txt info.txt

CMD ["ls"]

```

docker-compose.yml
```text
version: "3.8"

services:
  rm-storage:
    container_name: "rm-storage"
    image: rm-storage
    volumes:
      - data:/data

  rm-web:
    container_name: "rm-web"
    image: rm-web
    volumes:
      - data:/data
      - ./config:/application/config
    ports:
      - "8080:8080"

volumes:
  data:

```
주의할 점은 파일을 변경해서 image 를 빌드 후 docker-compose 로 새로 구성하여도 기존 공유 볼륨이 변경되지 않는다. 기존 볼륨을 삭제해야 새로 만들면서 반영된다.

## Troubleshooting
- Spring Boot container 접속시 Empty reply from server
    ```text
    server.address=0.0.0.0
    ```
## Refs
https://docs.docker.com/compose/gettingstarted/


