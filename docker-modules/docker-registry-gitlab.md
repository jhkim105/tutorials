

## Gitlab container registry 

### Create Access token
https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html#create-a-personal-access-token

### Docker Login

```text
cat ~/gitlab-cr.txt | docker login registry.gitlab.com --username jhkim105 --password-stdin
```

### Build
```text
docker build -t registry.gitlab.com/jhkim105/docker-practice .
```

### Push
```text
docker push registry.gitlab.com/jhkim105/docker-practice
```

### Logout
```text
docker logout registry.gitlab.com
```

### Run
```text
# 로그인 후
cat ~/gitlab-cr.txt | docker login registry.gitlab.com --username jhkim105 --password-stdin
# 실행
docker run -p 7070:8080 registry.gitlab.com/jhkim105/docker-practice
```

