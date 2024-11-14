

## Gitlab container registry 

### Create Access token
https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens

### Docker Login

```shell
cat ~/github-cr.txt | docker login ghcr.io --username jhkim105 --password-stdin
```

### Build
```shell
docker build -t ghcr.io/jhkim105/tutorials/spring-docker .
```

### Push
```shell
docker push ghcr.io/jhkim105/tutorials/spring-docker
```

### Delete
```shell
docker rmi -f $(docker images ghcr.io/jhkim105/tutorials/spring-docker -q)
yes | docker image prune
```


### Run
```shell
docker run -p 7070:8080 ghcr.io/jhkim105/tutorials/spring-docker
```

## References
- https://docs.github.com/ko/packages/working-with-a-github-packages-registry/working-with-the-container-registry

