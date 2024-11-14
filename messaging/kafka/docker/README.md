

## Kafka 실행
```shell
docker compose up -d
```

- Kafka UI
http://localhost:8989

- Topic 생성
```shell
docker exec -it kafka kafka-topics --bootstrap-server localhost:9092 --create --topic queue.sample --partitions 1 --replication-factor 1
```

## Kafka CLI
```shell
docker exec -it kafka /bin/bash


# create topic 'my-first-topic'
kafka-topics --bootstrap-server localhost:9092 --create --topic my-first-topic --partitions 1 --replication-factor 1

# list topics
kafka-topics --bootstrap-server localhost:9092 --list

# send messages to the topic
kafka-console-producer --bootstrap-server localhost:9092 --topic my-first-topic
```

## References

