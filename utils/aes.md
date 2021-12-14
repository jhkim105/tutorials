AES
=============



## openssl
```shell
openssl enc -d -aes-256-cbc -nosalt -K 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -iv 6162636465666768696a6b6c6d6e6f70 -in target/enc.txt -out target/dec-openssl.txt
```
-k key or (-pass pass:key) 옵션을 사용하면 iv는 입력받지 않고 자동 생성함
-p: key, iv 출력
```shell
openssl enc -e -aes-256-cbc -nosalt -k abcdefghijklmnop1234567890123456 -p -in src/test/resources/input.txt -out target/input-enc-256.txt
```


## Refs
https://wiki.openssl.org/index.php/Enc

