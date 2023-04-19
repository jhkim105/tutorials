JPA Locking
==================


## Pessimistic Locking
### Lock Modes
- PESSIMISTIC_READ: shared lock
- PESSIMISTIC_WRITE: exclusive lock
- PESSIMISTIC_FORCE_INCREMENT: PESSIMISTIC_WRITE and version 증가

PESSIMISTIC_READ 는  PESSIMISTIC_WRITE 가 있을 경우 획득할 수 없다  
PESSIMISTIC_WRITE 는 PESSIMISTIC_READ 나 PESSIMISTIC_WRITE 가 있을 경우 획득할 수 없다
PESSIMISTIC_READ 를 지원하지 않는 경우 PESSIMISTIC_WRITE 잠금을 얻을 수 있다.
잠금을 획득하지 못하면 PersistenceException 던짐

Setting a Pessimistic Lock
```
em.lock(employee, LockModeType.PESSIMISTIC_WRITE);
```

Pessimistic Lock Timeout (milliseconds)
```
javax.persistence.lock.timeout="1000"
```

Releasing a Pessimistic Lock
```
em.lock(employee, LockModeType.NONE);
```


 
## Optimistic Locking
- OPTIMISTIC (READ)
- OPTIMISTIC_FORCE_INCREMENT (WRITE)
  - 데이터 조회만 하는 경우에도 버전을 증가시킨다.
### Mapping optimistic locking
@Version 속성 지정
- numeric or timestamp
- @Version filed 가 있으면 default 로 LockModeType.OPTIMISTIC 이 적용됨. 
- Optimistic lock 을 사용하지 않으려면, entity class에 @OptimisticLocking(type = OptimisticLockType.NONE) 을 지정해야 함.


## Refs
https://mariadb.com/kb/en/innodb-lock-modes/
https://www.baeldung.com/jpa-pessimistic-locking
https://www.baeldung.com/jpa-optimistic-locking