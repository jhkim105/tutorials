docker build -t rabbitmq .
docker run -d --name rabbitmq -p 15672:15672 -p 5672:5672 -p 1883:1883 <image-id>

check plugin
$ docker exec -it rabbitmq /bin/bash
# rabbitmq-plugins list