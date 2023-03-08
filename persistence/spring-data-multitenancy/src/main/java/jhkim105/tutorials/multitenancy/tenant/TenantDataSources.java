package jhkim105.tutorials.multitenancy.tenant;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class TenantDataSources implements InitializingBean {

  private final TenantDataSourceCacheProperties properties;
  private final BasicDataSource dataSource;
  private final TenantRepository tenantRepository;

  private LoadingCache<String, DataSource> caches;

  @Override
  public void afterPropertiesSet() {
    createDataSourceCache();
  }

  private void createDataSourceCache() {
    log.info("DataSourceCache create. maxSize: {}, expireMinutes: {}", properties.getMaxSize(), properties.getExpireMinutes());
    caches = CacheBuilder.newBuilder()
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
  private DataSource createDataSource(Tenant tenant) {
    BasicDataSource tenantDataSource = new BasicDataSource();
    tenantDataSource.setUsername(tenant.getDbUsername());
    tenantDataSource.setPassword(tenant.getDbPassword());
    tenantDataSource.setUrl(tenant.getJdbcUrl());
    tenantDataSource.setMaxTotal(tenant.getMaxTotal());
    tenantDataSource.setMaxIdle(tenant.getMaxIdle());
    tenantDataSource.setMinIdle(tenant.getMinIdle());
    tenantDataSource.setInitialSize(tenant.getInitialSize());
    tenantDataSource.setTimeBetweenEvictionRunsMillis(dataSource.getTimeBetweenEvictionRunsMillis());
    tenantDataSource.setMinEvictableIdleTimeMillis(dataSource.getMinEvictableIdleTimeMillis());
    tenantDataSource.setDefaultAutoCommit(dataSource.getDefaultAutoCommit());
    log.debug("tenantDataSource created. id: {}, url:{}, maxTotal:{}, maxIdle:{}, minIdle:{}", tenant.getId(),
        tenantDataSource.getUrl(), tenantDataSource.getMaxTotal(), tenantDataSource.getMaxIdle(), tenantDataSource.getMinIdle());
    return tenantDataSource;
  }

  public DataSource get(String tenantId) {
    try {
      return caches.get(tenantId);
    } catch (ExecutionException e) {
      throw new IllegalStateException("Failed to load DataSource for tenant: " + tenantId);
    }
  }

  public long size() {
    return caches.size();
  }

  public DataSource getIfPresent(String id) {
    return caches.getIfPresent(id);
  }

  @Scheduled(fixedRate = 10_000)
  public void cleanUpCache() {
    caches.cleanUp();
    log.debug("tenantDataSources.cleanUp() executed.");
  }

  public void invalidate(String key) {
    caches.invalidate(key);
  }
}
