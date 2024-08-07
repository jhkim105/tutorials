# N + 1 Problem
연관관계가 설정된 엔터티를 조회하는 경우 조회된 데이터 갯수(n) 만큼 연관관계 조회쿼리가 추가로 발생하는 뮨재

## OneToOne Lazy
### 단건 조회시
OneToOne FetchType.LAZY 일 경우 추가쿼리가 실행됨
주키공유(식별관계, @MapsId) OneToOne, optional=false 인 경우에는 n+1 쿼리를 실행하지 않는다.

### 다건 조회시
참조관계(비식별관계) OneToOne 에 대한 n+1 select 발생함  
참조관계 OneToOne 이 optional=false 인 경우 2n + 1 발생함. 동일한 쿼리가 2번씩 실행됨 (버그 의심됨)


## Query 실행 횟수 검증 - quickperf
```
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.quickperf</groupId>
        <artifactId>quick-perf-bom</artifactId>
        <version>1.1.0</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.quickperf</groupId>
      <artifactId>quick-perf-junit5</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.quickperf</groupId>
      <artifactId>quick-perf-springboot2-sql-starter</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

@QuickPerfTest 애노테이션 추가하거나, 자동으로 감지하게 하려면 아래 설정 추가
- src/test/resources/junit-platform.properties
    ```
    junit.jupiter.extensions.autodetection.enabled=true
    ```

- Import QuickPerfSqlConfig.class
    ```
    @Import({JpaConfig.class, QuickPerfSqlConfig.class})
    ```

- @ExpectSelect() 사용하여 실행횟수 지정
    ```
    @ExpectSelect(2)
    void findAllUsingEntityGraph() {
    ```



## Summary
- JPQL 은 글로벌 페치전략 을 따르지 않는다.
- @OneToOne 은 optional 이 아닌 경우에는 주키공유(@MapsId)를 사용 권장
- @OneToMany 관계에서 @EntityGraph 또는 Fetch Join 사용시 Paging query (limit) 를 실행하지 않는다.
  - @EntityGraph: 전체를 조회 후 paging 처리
  - Fetch Join: 전체를 조회 후 한건만 리턴함 (이전 버전에서는 예외가 발생했음)
    ```text
    Caused by: org.hibernate.QueryException: query specified join fetching, but the owner of the fetched association was not present in the select list [FromElement{explicit,not a collection join,fetch join,fetch non-lazy
    ```


