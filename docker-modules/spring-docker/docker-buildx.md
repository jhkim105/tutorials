

## buildx
Create builder
```shell
docker buildx create --use --name builder desktop-linux
```

Build & Push
```shell
docker buildx build --platform=linux/amd64,linux/arm64 -t registry.gitlab.com/jhkim105/docker-practice/spring-docker-x:latest --push .
```

Inspect
```shell
docker buildx imagetools inspect registry.gitlab.com/jhkim105/docker-practice/spring-docker-x
```

Run

### Run
```shell
docker run -p 7070:8080 registry.gitlab.com/jhkim105/docker-practice/spring-docker-x
```

## References
- https://docs.docker.com/build/building/multi-platform/
- https://github.com/docker/buildx?tab=readme-ov-file

