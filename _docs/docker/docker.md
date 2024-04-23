# Docker Tip


## Docker 자동실행
### docker 실행시 다음 옵션 추가
--restart unless-stopped

### 이미 실행중인 컨테이너에 적용하기
docker update --restart unless-stopped mariadb

--restart option
no, on-failure, always, unless-stopped


##  docker data 폴더 변경하기
```
docker info
systemctl stop docker.service
systemctl stop docker.socket
cp -R /var/lib/docker /DATA/docker
vi /lib/systemd/system/docker.service
# ExecStart 에 -g 신규디렉토리 추가
ExecStart=/usr/bin/dockerd -g /DATA/docker -H fd:// --containerd=/run/containerd/containerd.sock
```


systemctl daemon-reload
systemctl start docker.service

##  Volume Mount 확인하기
docker inspect -f "{{ .Mounts }}" container-name
```text
➜  docker git:(master) ✗ docker inspect -f "{{ .Mounts }}" prometheus
[{bind  /Users/jihwankim/dev/docker/prometheus/data /etc/prometheus   true rprivate} {volume 0d12d2562032a3f06f344107e7bc64a58f04aac330bbd6378a8f1e82ba91d0c8 /var/lib/docker/volumes/0d12d2562032a3f06f344107e7bc64a58f04aac330bbd6378a8f1e82ba91d0c8/_data /prometheus local  true }]

```

##  Container 를 Image 로 커밋하기
docker commit -m "메시지" 컨테이너명 이미지명:태그


##  Volumes
```text
docker volume ls
docker volume inspect pinpoint-docker_mysql_data
docker volume rm pinpoint-docker_mysql_data

```

## Prune
사용하지 않는 컨테이너/이미지 일괄 삭제
```text
# 중지된 모든 컨테이너 삭제
$ docker container prune

# 태깅이 되지 않은 모든 이미지(dangling image) 삭제
$ docker image prune

# 사용되지 않는 도커 네트워크 삭제
$ docker network prune

# 컨테이너에서 사용하지 않는 모든 도커 볼륨 삭제
$ docker volume prune

# 모든 리소스 (컨테이너, 이미지, 네트워크 등) 중 사용하지 않는 리소스 삭제
$ docker system prune -a
```
image 가 삭제되지 않고 남아 있는 경우 아래 명령으로 삭제시도하면 사용중
```text
docker rmi $(docker images -f "dangling=true" -q)
```
->
```shell
Error response from daemon: conflict: unable to delete db32aed95b01 (must be forced) - image is being used by stopped container ca2f2e33702c
Error response from daemon: conflict: unable to delete 8fe1268f7741 (must be forced) - image is being used by stopped container 7ca091d044e0
```
container 삭제 후 재시도하면 삭제됨
```
docker rm ca2f2e33702c
docker rm 7ca091d044e0
docker rmi $(docker images -f "dangling=true" -q)
```

## Network
Container 에서 host 주소는 host.docker.internal 로 접속


## How To Change The Default Docker Subnet IP Range

- sudo vi /etc/docker/daemon.json
    - "bip" 로 대역 지정
```json
{
  "log-driver": "journald",
  "log-opts": {
    "tag": "{{.Name}}"
  },
  "bip": "172.26.0.1/16"
}
```

