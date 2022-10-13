JPA Locking
==================


## JPA Pessimistic Locking
- PESSIMISTIC_READ: shared lock
- PESSIMISTIC_WRITE: exclusive lock
- PESSIMISTIC_FORCE_INCREMENT: PESSIMISTIC_WRITE and version 증가
    
## Optimistic Locking
- OPTIMISTIC (READ)
- OPTIMISTIC_FORCE_INCREMENT (WRITE)

## Refs
https://mariadb.com/kb/en/innodb-lock-modes/
https://www.baeldung.com/jpa-pessimistic-locking
https://www.baeldung.com/jpa-optimistic-locking