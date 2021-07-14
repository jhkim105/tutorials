Master Slave Replication using Replication Driver
==============================================

## Config Master/Slave with Docker

### Master config
```
[mysqld]
log-bin=mysql-bin
server-id=1
expire_logs_days=3
```

### Slave config
```
[mysqld]
server-id=2
#replicate-do-db=demo_repl
log-slave_updates=1
read-only=1
```

### Run Master container
```
docker container run -d -p 13306:3306   \
-e MYSQL_ROOT_PASSWORD=111111   \
-v /Users/jihwankim/dev/docker/mariadb-master-slave/data/master:/var/lib/mysql   \
-v /Users/jihwankim/dev/docker/mariadb-master-slave/conf/master:/etc/mysql/conf.d   \
--name mariadb-master mariadb
```

### Run Slave container
```
docker container run -d -p 23306:3306   \
-e MYSQL_ROOT_PASSWORD=111111   \
-v /Users/jihwankim/dev/docker/mariadb-master-slave/data/slave:/var/lib/mysql      \
-v /Users/jihwankim/dev/docker/mariadb-master-slave/conf/slave:/etc/mysql/conf.d      \
--link mariadb-master   \
--name mariadb-slave mariadb
```

### CREATE USER on Master
```
mysql -u root -p111111 --port 13306 --host 127.0.0.1
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%' IDENTIFIED BY '111111';
```

### On Master
```
FLUSH TABLES WITH READ LOCK;
SHOW MASTER STATUS;
UNLOCK TABLES;
```


### Start Slave
```
mysql -u root -p111111 --port 23306 --host 127.0.0.1
CHANGE MASTER TO MASTER_HOST='mariadb-master', MASTER_USER='repl', MASTER_PASSWORD='111111', MASTER_LOG_FILE='mysql-bin.000005', MASTER_LOG_POS=342;
START SLAVE;
SHOW SLAVE STATUS;
```

## Replication Driver 적용하기
```properties
url: jdbc:mariadb:replication://127.0.0.1:13306,127.0.0.1:23306,127.0.0.1:33306/demo_repl?createDatabaseIfNotExist=true&serverTimezone=Asia/Seoul
```
### DB 커넥션 수 Test
* Evictor thread 관련 속성
  - time-between-eviction-runs-millis: 기본값 -1. evictor thread 샐행 간격.
  - min-evictable-idle-time-millis: 기본값 30분(1800000). 이 시간이 지난 커넥션들은 evictor thread 가 커넥션을 종료시킨다.
  - num-tests-per-eviction-run: 기본값 4. 한번에 검사할 커넥션 수.
  
```properties
initial-size: 30
max-idle: 30
min-idle: 10
time-between-eviction-runs-millis: 5000
min-evictable-idle-time-millis: 1000
```

* Replication Driver 사용시 slave DB는 설정된 커넥션 수를 분배해서 생성한다.
  - 웹서버 시작시 master는 30개, slave는 각각 15개씩 커넥션을 생성힌다.
  - 5초마다 EvictorThread가 실행되면서 min-idle 갯수만큼 커넥션을 유지해야하는데, 촐량은 유지되지만, slave 각각 균등하게 접속을 유지하지는 않는다.
    - master:10, slave1: 3, slave2: 7
    - slave 커넥션 숫자는 계속 변함. 총량은 유지됨.
    

### 부하 상황에서 커넥션 수
```properties
max-total: 5
initial-size: 5
max-idle: 5
min-idle: 5
```
시작시에는 slave 총량이 5개로 유지되었다가 부하가 발생할 경우 슬레이브의 각 커넥션수는 max-total 값 까지 증가한다. 이런 경우 master db는 slave 총량 갯수 만큼 늘어서 10개가 된다.  

이번 테스트를 통해 커넥션 유지를 위해서 max-total, max-idle, intial-size, min-idle을 동일하게 설정했는데, 커넥션이 계속 증가하는 상황을 검증 할 수 있었다.



