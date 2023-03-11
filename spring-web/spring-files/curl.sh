#curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/archive.zip -o /dev/null
#curl -H 'accept-encoding: gzip' -H 'accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2' -sS -D - http://localhost:8888/archive.zip -o /dev/null
#curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/info.txt -o /dev/null
#curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/info.txt.zip -o /dev/null
#curl -sS -D - http://localhost:8081/archive.zip -o /dev/null
#curl -H 'accept-encoding: *' -sS -D - http://localhost:8081/archive.zip -o /dev/null
#curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/files/archive.zip -o /dev/null

curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/image.png -o /dev/null
curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/files/image.png -o /dev/null
curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/archive.zip -o /dev/null
curl -H 'accept-encoding: gzip' -sS -D - http://localhost:8081/files/archive.zip -o /dev/null