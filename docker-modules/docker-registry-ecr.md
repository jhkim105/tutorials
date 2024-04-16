
## Setup
- [awscli](https://docs.aws.amazon.com/ko_kr/cli/v1/userguide/install-macos.html)
- [amazon-ecr-credential-helper](https://github.com/awslabs/amazon-ecr-credential-helper) 


## Permission
```text
{
	"Version": "2012-10-17",
	"Statement": [
		{
			"Sid": "Statement1",
			"Effect": "Allow",
			"Action": [
				"ecr:GetAuthorizationToken",
				"ecr:BatchGetImage",
				"ecr:GetDownloadUrlForLayer"
			],
			"Resource": "*"
		}
	]
}
```

## Get token and Login
```shell
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 002436513987.dkr.ecr.ap-northeast-2.amazonaws.com
```

