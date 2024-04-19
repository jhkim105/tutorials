

## Gitlab container registry 

### Create Access token
https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html#create-a-personal-access-token

### Docker Login

```shell
cat ~/gitlab-cr.txt | docker login registry.gitlab.com --username jhkim105 --password-stdin
```

### Build
```shell
docker build -t registry.gitlab.com/jhkim105/docker-practice/spring-docker .
```

### Push
```shell
docker push registry.gitlab.com/jhkim105/docker-practice/spring-docker
```

### Delete
```shell
docker rmi -f $(docker images registry.gitlab.com/jhkim105/docker-practices/pring-docker -q)
yes | docker image prune
```


### Run
```shell
docker run -p 7070:8080 registry.gitlab.com/jhkim105/docker-practice/spring-docker
```

## 기타

### 사설 리파지토리 접속시 509 에러나는 경우
```
Error response from daemon: Get "https://gitlab.rsupport.com:5050/v2/": x509: certificate signed by unknown authority
Build step 'Execute shell' marked build as failure
```

- 인증서를 추가하거나
```text
mkdir -p /etc/docker/certs.d/gitlab.rsupport.com:5050
openssl s_client -showcerts -connect gitlab.rsupport.com:5050 < /dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > /etc/docker/certs.d/gitlab.rsupport.com:5050/ca.crt
/etc/docker/certs.d/gitlab.rsupport.com:5050/ca.crt
```

- 설정 추가 (insecure-registries)
  ~/.docker/daemon.json
```text
{
  "insecure-registries" : ["gitlab.rsupport.com:5050","gitlab.rsupport.com:5050"]
}
```


## References
- https://docs.gitlab.com/ee/user/packages/container_registry/

