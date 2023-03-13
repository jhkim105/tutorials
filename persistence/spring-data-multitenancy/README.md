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
- Master/Tenant Database
- Tenant 식별하기
- Tenant Database 생성/업데이트

### Tenant DataSource Caching
Tenant DataSource 를 cache 에 저장하고 만료될때 connection 을 반환하도록 한다.
```xml
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>27.0.1-jre</version>
    </dependency>
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

### TaskDecoder 를 사용하여 context 전파하기
@Async 를 사용하면 별도의 thread 로 실행되기 때문에 ThreadLocal 에 저장한 변수값이 전파되지 않는다. TaskDecoder 를 사용하여 이 문제를 해결할 수 있다




## References
https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#multitenacy 
https://aws.amazon.com/ko/blogs/apn/building-a-multi-tenant-saas-solution-using-aws-serverless-services/
https://www.baeldung.com/hibernate-5-multitenancy
https://www.baeldung.com/multitenancy-with-spring-data-jpa
