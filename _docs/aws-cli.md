AWS CLI
==================

## configure
```shell
aws configure --profile rm-s3
```


## S3
### File Download
```shell
aws s3 cp s3://rtmpublic-dev/2.21.1 . --recursive --profile rm-s3
```

```shell
aws s3 sync s3://rtmpublic/2.28.3 ./public --profile s3
```

### File Upload
aws s3 sync ./test s3://rtmpublic-dev/test --profile rm-s3


## Refs
https://docs.aws.amazon.com/cli/latest/userguide/cli-services-s3-commands.html

