# key: hex(1234567890abcdef)

# Encrypt - nosalt
#openssl enc -aes-128-cbc --nosalt --in input.txt -out enc-openssl.txt -k 31323334353637383930616263646566 -p
#openssl enc -aes-128-cbc --nosalt --in input.txt -out enc-openssl.txt -pass pass:31323334353637383930616263646566  -p
#openssl enc -aes-128-cbc --nosalt --in input.txt -out enc-openssl.txt -pass pass:31323334353637383930616263646566 -md sha256 -p
# Decrypt - nosalt
#openssl enc -d -aes-128-cbc -nosalt --in enc-openssl.txt -out dec.txt -k 31323334353637383930616263646566 -debug
#openssl enc -d -aes-128-cbc -nosalt --in enc-openssl.txt -out dec.txt -K 61FEB9C8A7000372181997A660798F89 -iv D00446D6C850A0771301EC74F9DE27A7


# Encrypt - salt
openssl enc -aes-128-cbc --in input.txt -out enc-openssl-salt.txt -k 31323334353637383930616263646566 -p
#openssl enc -aes-128-cbc --in input.txt -out enc-openssl-salt.txt -pass pass:31323334353637383930616263646566 -md sha256 -p
#openssl enc -aes-128-cbc --in input.txt -out enc-openssl-salt.txt -pass pass:31323334353637383930616263646566 -p


