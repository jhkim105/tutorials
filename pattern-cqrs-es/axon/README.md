Axon
=================

## Dependency
```xml
  <dependencies>
    <dependency>
      <groupId>org.axonframework</groupId>
      <artifactId>axon-spring-boot-starter</artifactId>
      <version>4.5.15</version>
    </dependency>
    <dependency>
      <groupId>org.axonframework</groupId>
      <artifactId>axon-test</artifactId>
      <scope>test</scope>
      <version>4.5.15</version>
    </dependency>
  </dependencies>
```

## Axon Server
### Download And Run
https://download.axoniq.io/axonserver/AxonServer.zip  
```shell
java -jar axonserver.jar
```

### Docker
```shell
docker run \
  -d \
  --platform linux/amd64 \
  --name axon_server \
  -p 8024:8024 \
  -p 8124:8124 \
  -e AXONIQ_AXONSERVER_NAME=order_demo \
  axoniq/axonserver
```

http://localhost:8024

## Command Model
- Command
- Event
- Aggregate
  - @CommandHandler
  - @EventSourcingHandler

## Query Model
- Domain
- EventHandler
  - @EventHandler
- QueryHandler
  - @QueryHandler
  
    
## Endpoint(Controller)




