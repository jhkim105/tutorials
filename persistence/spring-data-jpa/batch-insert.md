
## Batch Insert

```properties
spring.jpa.properties.hibernate.jdbc.batch_size=4
```

```java
  @Test
void test() {
  var products = List.of(new Product("p01", 1000l), new Product("p02", 2000l));
  productRepository.saveAll(products);
}

```


### 실행결과

batch_size 지정 안한 경우
```
    768875 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    63292 nanoseconds spent preparing 2 JDBC statements;
    1706334 nanoseconds spent executing 2 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    10035792 nanoseconds spent executing 1 flushes (flushing a total of 2 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
```

batch_size 지정한 경우
```
    666750 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    49334 nanoseconds spent preparing 1 JDBC statements;
    0 nanoseconds spent executing 0 JDBC statements;
    2135375 nanoseconds spent executing 1 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    11180250 nanoseconds spent executing 1 flushes (flushing a total of 2 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
```

general_log 확인
```shell
docker exec -it mariadb sh -c 'mysql -uroot -p -e "SET GLOBAL general_log=1"'
```
```shell
docker exec -it mariadb sh -c 'tail -f /var/lib/mysql/$HOSTNAME.log'
```

batch_size 지정 안한 경우
```
240610 14:49:51  18807 Query    set autocommit=0
                 18807 Query    insert into de_product (name,price,id) values ('p01',1000,'6f14a4f6-cd2c-4313-9a74-ab93844d03f5')
                 18807 Query    insert into de_product (name,price,id) values ('p02',2000,'f26ac1ac-38a3-49e2-bc7a-3e0f6de00de0')
                 18807 Query    COMMIT
                 18807 Query    set autocommit=1
```

batch_size 지정한 경우
```
                 18817 Query    set autocommit=0
                 18817 Prepare  insert into de_product (name,price,id) values (?,?,?)
                 18817 Execute  insert into de_product (name,price,id) values (?,?,?)
                 18817 Close stmt       
                 18817 Query    COMMIT
                 18817 Query    set autocommit=1

```