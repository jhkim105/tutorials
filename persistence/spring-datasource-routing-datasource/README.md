RoutingDataSource를 사용하여 Master/Slave 접속 구현
==============================================

## Master-Slave
DataSourceConfig.java
```java
@Configuration
public class DataSourceConfig {

  @Bean
  @ConfigurationProperties("spring.datasource.master")
  public DataSourceProperties masterDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.master.dbcp2")
  public DataSource masterDataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
  }


  @Bean
  @ConfigurationProperties("spring.datasource.slave")
  public DataSourceProperties slaveDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.slave.dbcp2")
  public DataSource slaveDataSource(@Qualifier("slaveDataSourceProperties") DataSourceProperties dataSourceProperties) {
    return dataSourceProperties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
  }

  @Bean
  public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
      @Qualifier("slaveDataSource") DataSource slaveDataSource) {

    ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

    Map<Object, Object> dataSourceMap = new HashMap<>();
    dataSourceMap.put("master", masterDataSource);
    dataSourceMap.put("slave", slaveDataSource);
    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @Bean
  @Primary
  public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

}
```

ReplicationRoutingDataSource.java
```java
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceType =
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "slave" : "master";
        return dataSourceType;
    }
}
```

application.yml
```yml
spring:
  datasource:
    master:
      type: org.apache.commons.dbcp2.BasicDataSource
      url: jdbc:mariadb://127.0.0.1:13306/demo_repl?createDatabaseIfNotExist=true
      username: root
      password: 111111
      dbcp2:
        max-wait-millis: 1000
        max-total: 5
        initial-size: 5
        max-idle: 5
        min-idle: 2
        min-evictable-idle-time-millis: 10000
        time-between-eviction-runs-millis: 5000
    slave:
      type: org.apache.commons.dbcp2.BasicDataSource
      url: jdbc:mariadb://127.0.0.1:23306/demo_repl?createDatabaseIfNotExist=true
      username: root
      password: 111111
      dbcp2:
        max-wait-millis: 1000
        max-total: 10
        initial-size: 10
        max-idle: 10
        min-idle: 5
        min-evictable-idle-time-millis: 10000
        time-between-eviction-runs-millis: 5000
```
Evictor thread 설정 테스트를 위해서 min-evictable-idle-time-millis 를 10초, time-between-eviction-runs-millis 를 5초로 설정  
slave connection 은 웹서버 시작시에는 생성되지 않았다가, 요청이 발생하면 생성됨.  
Evictor thread 잘 동작함.(minIdle 유지 확인)
읽기 부하 발생시 master db 커넥션은 증가(replication driver 단점)하지 않는다.

## 두개 이상의 slave
```yml
url: jdbc:mariadb:loadbalance://127.0.0.1:23306,127.0.0.1:33306/demo_repl?createDatabaseIfNotExist=true
```
replication driver 처럼 설정값이 분배되어 생성됨.







