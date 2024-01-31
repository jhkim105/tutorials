

## UUID Creator
https://github.com/f4b6a3/uuid-creator

```xml
<dependency>
    <groupId>com.github.f4b6a3</groupId>
    <artifactId>uuid-creator</artifactId>
    <version>5.3.7</version>
</dependency>
```

```java
    System.out.println("v1: " + UuidCreator.getTimeBased());
    System.out.println("v6: " + UuidCreator.getTimeOrdered());
    System.out.println("v7: " + UuidCreator.getTimeOrderedEpoch());
```

```text
v1: 2c8f8d2e-bf79-11ee-9fcd-a56b5d731c15
v6: 1eebf792-c8f9-64ca-b7db-09a91c77c919
v7: 018d5ab4-0bd8-7399-b585-68b05c65df0c
```

```text
Time-based UUID structure

 00000000-0000-v000-m000-000000000000
|1-----------------|2---|3-----------|

1: timestamp
2: clock-sequence
3: node identifier
```
Benchmark
```text
[UUID.randomUUID()] 12800000 UUIDs generated,  0 collisions in 11729 ms
[UuidCreator.getTimeBased()] 12800000 UUIDs generated,  0 collisions in 2503 ms
```

[benchmark of uuid-creator](https://github.com/f4b6a3/uuid-creator/wiki/5.0.-Benchmark)



## References
[Baeldung](https://www.baeldung.com/java-generating-time-based-uuids)
[uuid-creator](https://github.com/f4b6a3/uuid-creator)
