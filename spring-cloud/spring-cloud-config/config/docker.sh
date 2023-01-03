docker run --rm -it -p 8888:8888 -m=2g \
      --platform linux/amd64 \
      -v /Users/jihwankim/dev/my/tutorials/spring-cloud/spring-cloud-config/config:/config \
      -e SPRING_PROFILES_ACTIVE=native,cloud-bus-rabbit  \
      -e SPRING_RABBITMQ_HOST=host.docker.internal \
      -e SPRING_RABBITMQ_USERNAME=guest \
      -e SPRING_RABBITMQ_PASSWORD=guest \
      -e SPRING_RABBITMQ_VIRTUAL_HOST=rmlocal \
      hyness/spring-cloud-config-server