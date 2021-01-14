# DBPC Test
## DB connection 갯수에 관련된 설정들
https://commons.apache.org/proper/commons-dbcp/configuration.html

* maxActive(8) : 서비스에서 동시에 사용될수 있는 최대 커넥션 개수.

* maxIdle(8) : 커넥션 풀에 유지될수 있는 커넥션의 최대 개수. 커넥션 풀에 커넥션을 반납할 때 종료시킴.
  - (maxAcive - maxIdle ) 개의 커넥션은 pool 에 반환되지 않고 제거(real destory) 된다.  
  - The maximum number of connections that can remain idle in the pool, without extra ones being released, or negative for no limit
  - NOTE: If maxIdle is set too low on heavily loaded systems it is possible you will see connections being closed and almost immediately new connections being opened. This is a result of the active threads momentarily closing connections faster than they are opening them, causing the number of idle connections to rise above maxIdle. The best value for maxIdle for heavily loaded system will vary but the default is a good starting point.

* minIdle(0) : 커넥션 풀에 유지되수 있는 idle 상태 커넥션의 최소 개수. 

* validationQuery : 커넥션의 유효성 검증에 사용할 SQL Query (반드시 하나 이상의 row 가 반환될 SELECT 구분이어야 함.)

* testOnBorrow(true) : 커넥션 풀에서 커넥션을 가져올때 해당 커넥션의 유효성 검사를 할것인지 여부. 반드시 validationQuery 가 설정되어 있어야 함.

* testOnReturn(false) : testOnBorrow 와 비슷함. 다만 유효성 검사 시점이 커넥션을 풀에 반환할때 이다.

* testWhileIde(false) : 커넥션 유효성 검사를 풀에 idle 상태에 존재할때 실시할것인지 여부

* timeBetweenEvictionRunsMillis(-1): Evictor 스레드 실행 간격. 기본값은 -1이며 Evictor 스레드의 실행이 비활성화 되어 있다. 유휴 커넥션을 제거하고 minIdle 로 커넥션을 유지한다.

* numTestsPerEvictionRun(3): Evictor 스레드 동작 시 한 번에 검사할 커넥션의 개수

* minEvictableIdleTimeMillis(1000 * 60 * 30): 유휴 커넥션 판단 기준 시간. (기본값: 30분). 커넥션 숫자를 적극적으로 줄여야 하는 상황이 아니라면 minEvictableIdleTimeMillis 속성값을 -1로 설정해서 해당 기능을 사용하지 않기를 권장
  - The minimum amount of time an object may sit idle in the pool before it is eligible for eviction by the idle object evictor (if any).

* softMinEvictableIdleTimeMillis(-1): 풀에 유휴상태로 남아있을 수 있는 최소시간. 
  - The minimum amount of time a connection may sit idle in the pool before it is eligible for eviction by the idle connection evictor, with the extra condition that at least "minIdle" connections remain in the pool. When minEvictableIdleTimeMillis is set to a positive value, minEvictableIdleTimeMillis is examined first by the idle connection evictor - i.e. when idle connections are visited by the evictor, idle time is first compared against minEvictableIdleTimeMillis (without considering the number of idle connections in the pool) and then against softMinEvictableIdleTimeMillis, including the minIdle constraint.
  
## Test
* connection 조회
```
select * from information_schema.processlist where DB = 'demo';
```  
