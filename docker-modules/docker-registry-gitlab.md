

## Gitlab container registry 

### Create Access token
https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html#create-a-personal-access-token

### Docker Login

```shell
cat ~/gitlab-cr.txt | docker login registry.gitlab.com --username jhkim105 --password-stdin
```

### Build
```shell
docker build -t registry.gitlab.com/jhkim105/docker-practice .
```

### Push
```shell
docker push registry.gitlab.com/jhkim105/docker-practice
```

### Delete
```shell
docker rmi -f $(docker images registry.gitlab.com/jhkim105/docker-practice -q)
yes | docker image prune
```


### Run
```shell
docker run -p 7070:8080 registry.gitlab.com/jhkim105/docker-practice
```

## References
- https://docs.gitlab.com/ee/user/packages/container_registry/

