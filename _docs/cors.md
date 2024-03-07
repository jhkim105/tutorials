


## CORS TEST
```shell
curl -v -X OPTIONS https://www.remotemeeting.com \
-H 'Origin: https://www6.remotemeeting.com' 
```

```shell
curl -v -X OPTIONS https://www.remotemeeting.com \
-H 'Origin: https://www6.remotemeeting.com' \
-H 'Access-Control-Request-Headers: Origin, Accept, Content-Type' \
-H 'Access-Control-Request-Method: GET'
```


## S3 Bucket CORS 설정
```json
[
    {
        "AllowedHeaders": [
            "*"
        ],
        "AllowedMethods": [
            "GET",
            "HEAD"
        ],
        "AllowedOrigins": [
            "*"
        ],
        "ExposeHeaders": [],
        "MaxAgeSeconds": 3000
    }
]
```

## Refs
https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

