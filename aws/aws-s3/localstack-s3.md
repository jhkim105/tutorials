LocalStack S3
=========================

```yaml
version: "3.9"
services:
  localstack:
    image: localstack/localstack
    container_name: "localstack"
    ports:
      - "4566:4566"
    environment:
      - SERVICES=s3
      - USE_SINGLE_REGION=1
      - DEFAULT_REGION=ap-northeast-2
      - DOCKER_HOST=unix:///var/run/docker.sock
    volumes:
      - "/tmp/localstack:/var/lib/localstack"
      - "/var/run/docker.sock:/var/run/docker.sock"
      - "./localstack-init.d:/docker-entrypoint-initaws.d"
```

## CLI Command
- s3 
```shell
$ awslocal s3 ls
```

- s3api
```shell
$ awslocal s3api list-buckets
$ awslocal s3api create-bucket --bucket sample-bucket
$ awslocal s3api put-object --bucket sample-bucket --key test.txt --body test.txt
$ awslocal s3api list-objects --bucket sample-bucket
$ awslocal s3api delete-object --bucket sample-bucket --key test.txt
````

## 접속하기
- Path style (localhost/<bucket-name>)
  - http://localhost:4566/sample-bucket/test.txt    
- Host style (<bucket-name>.s3.<region>.localhost.localstack.cloud)
  - http://sample-bucket.s3.ap-northeast-2.localhost.localstack.cloud:4566/test.txt


## Refs
[LocalStack - S3](https://docs.localstack.cloud/user-guide/aws/s3/)




