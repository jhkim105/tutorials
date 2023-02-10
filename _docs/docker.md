Docker
===========


## AWS Linux2에 docker, docker-compose 설치하기

```shell
sudo amazon-linux-extras install docker
```

data directory  변경
```text
sudo vi /etc/docker/daemon.json

{
"data-root": "/DATA/docker/data"
}

sudo systemctl docker start
sudo docker info
```


auto-start
```shell
sudo systemctl enable docker.service
```

webdev1 계정으로 docker 실행
```shell
sudo usermod -a -G docker webdev1
sudo reboot
```

docker-compose
```shell
sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose version
```


