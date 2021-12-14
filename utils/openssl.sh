#openssl enc -d -aes-256-cbc -nosalt -K 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -iv 6162636465666768696a6b6c6d6e6f70 -in target/enc.txt -out target/dec-openssl.txt

openssl enc -e -aes-256-cbc -nosalt -k 6162636465666768696a6b6c6d6e6f7031323334353637383930313233343536 -p -in src/test/resources/input.txt -out target/input-enc-256.txt
#openssl enc -d -aes-256-cbc -nosalt -k abcdefghijklmnop1234567890123456 -in target/input-enc-256.txt -out target/input-dec-256.txt