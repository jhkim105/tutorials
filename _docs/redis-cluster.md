

## Redis Cluster 구성하기

### 구성
- node 마다 directory 생성 후 아래 설정 추가 (port 각각 지정)
- redis.conf
```properties
port 6381
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes
```
### redis 실행
```shell
cd <node_dir>
redis-server redis.conf
```

### cluster 시작
```shell
redis-cli --cluster create 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383
```
- --cluster-replicas 1: replica 갯수 지정
- 입력한 순서대로 master/slave 가 구성됨
```shell
redis-cli --cluster create 127.0.0.1:6381 127.0.0.1:6382 127.0.0.1:6383 127.0.0.1:6391 127.0.0.1:6392 127.0.0.1:6393 --cluster-replicas 1
```
- cluster info: cluster 정보
- cluster nodes: node 정보
- cluster reset: 기존 클러스터 구성 해제

### cluster 초기화
```shell
redis-cli --cluster call 127.0.0.1:6381 flushall
redis-cli --cluster call 127.0.0.1:6381 cluster reset
```
### 노드 추가

- master node 추가
```shel
# redis-cli --cluster add-node <신규 노드> <기존 노드>
redis-cli --cluster add-node 127.0.0.1:6384 127.0.0.1:6381

# resharding
redis-cli --cluster reshard 127.0.0.1:6381
```  

- slave node 추가
```shell
# redis-cli --cluster add-node <신규노드> <기존노드> --cluster-slave --cluster-master-id <master id>
redis-cli --cluster add-node 127.0.0.1:6394 127.0.0.1:6381 --cluster-slave --cluster-master-id d6a275651d1baaa736181c36893b468faaa0e0f8
```

### 노드 제거
- redis-cli --cluster del-node <cluster node>  <제거할 node id>
- resharding 후 진행해야 함

