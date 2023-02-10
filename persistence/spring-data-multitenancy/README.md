Multi-Tenancy with Spring Data JPA
==================================

## Multitenancy in Hibernate
* SCHEMA
  - MultiTenantConnectionProvider 를 구현해야 한다.
* DATABASE
  - MultiTenantConnectionProvider 를 구현해야 한다.
* DISCRIMINATOR
  - 지원안함

### MultiTenantConnectionProvider
- 테넌트마다 지정된 DB 커넥션정보를 제공
- hibernate.multi_tenant_connection_provider 프로퍼티로 지정
- 지정하지 않으면 DataSourceBasedMultiTenantConnectionProviderImpl를 사용

### CurrentTenantIdentifierResolver
- tenant 식별하기
- hibernate.tenant_identifier_resolver 로 지정

## 구현
- Master Database Configuration
- Tenant Database Configuration
- Tenant 식별하기
- Tenant Database 생성하기
- Tenant Migration Using Flyway
- Tenant DataSource Caching

### Tenant DataSource Caching
Tenant DataSource 를 cache 에 저장하고 만료될때 connection 을 반환하도록 한다.
```xml
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>27.0.1-jre</version>
    </dependency>
```
최신버전 (27.1-jre 이상) 을 사용하면 build error. Maven build 또는 java -jar 로 실행시에는 문제 없으나, 인텔리제이에서 테스트케이스 실행 또는 Application 을 실행하면 발생함.
```
java: package com.google.common.cache does not exist'
```

```java
  private void createDataSourceCache() {
    cacheConf = CacheBuilder.newBuilder()
        .maximumSize(properties.getMaxSize())
        .expireAfterAccess(properties.getExpireMinutes(), TimeUnit.MINUTES)
        .removalListener((RemovalListener<String, DataSource>) removal -> {
          BasicDataSource ds = (BasicDataSource) removal.getValue();
          try {
            ds.close();
            log.info("Closed datasource(url:[{}]).", ds.getUrl());
          } catch (SQLException e) {
            log.warn(e.toString());
          }
        })
        .build(new CacheLoader<>() {
          public DataSource load(String key) {
            Tenant tenant = tenantRepository.findById(key)
                .orElseThrow(() -> new IllegalStateException(String.format("Tenant not exists. id([%s])", key)));
            return createDataSource(tenant);
          }
        });
  }
```

만료된 캐시는 자동으로 삭제되지 않음. 해당 캐시를 접근하거나, maximum size 에 도달했을때 삭제 됨.  
https://github.com/google/guava/wiki/CachesExplained#when-does-cleanup-happen  
주기적으로 캐시를 정리하고 싶으면 주기적(ScheduledExecutorService)으로 Cache.cleanup() 을 호출해야 한다.  
```java

  @Scheduled(fixedRate = 10_000)
  public void cleanUpCache() {
    cacheConf.cleanUp();
  }

```

## References
[Hibernate - Multitenancy](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy)  
