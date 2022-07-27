package jhkim105.tutorials.multitenancy.tenant;

import static jhkim105.tutorials.multitenancy.master.domain.Tenant.DEFAULT_TENANT_ID;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.domain.TenantDeleteEvent;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private static final long serialVersionUID = 2353465673130594043L;

  @Autowired
  private TenantDataSourceCacheProperties tenantDataSourceCacheProperties;
  @Autowired
  private BasicDataSource dataSource;
  @Autowired
  private TenantRepository tenantRepository;
  @Autowired
  private TenantDatabaseHelper tenantDatabaseHelper;

  @Getter
  private LoadingCache<String, DataSource> tenantDataSources;


  @Override
  protected DataSource selectAnyDataSource() {
    log.info("selectAnyDataSource: masterDataSource selected.");
    return dataSource;
  }


  @Override
  protected DataSource selectDataSource(String tenantId) {
    if (DEFAULT_TENANT_ID.equals(tenantId)) {
      log.info("MasterDataSource selected");
      return dataSource;
    }
    try {
      BasicDataSource tenantDataSource = (BasicDataSource)tenantDataSources.get(tenantId);
      log.info("Tenant DataSource selected. url: [{}], cacheSize: [{}]", tenantDataSource.getUrl(), tenantDataSources.size());
      return tenantDataSource;
    } catch (ExecutionException e) {
      throw new IllegalStateException("Failed to load DataSource for tenant: " + tenantId);
    }
  }

  @PostConstruct
  void createDataSourceCache() {
    tenantDataSources = CacheBuilder.newBuilder()
        .maximumSize(tenantDataSourceCacheProperties.getMaxSize())
        .expireAfterAccess(tenantDataSourceCacheProperties.getExpireMinutes(), TimeUnit.MINUTES)
        .removalListener((RemovalListener<String, DataSource>) removal -> {
          BasicDataSource ds = (BasicDataSource)removal.getValue();
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
    log.info("TenantDataSource created. id: {}, url: {}, maxTotal: {}, maxIdle: {}, minIdle: {}",
        tenant.getId(),
        tenantDataSource.getUrl(), tenantDataSource.getMaxTotal(), tenantDataSource.getMaxIdle(), tenantDataSource.getMinIdle());
    return tenantDataSource;
  }



  @Scheduled(fixedRate = 10_000)
  public void cleanUpCache() {
    this.tenantDataSources.cleanUp();
  }

  @EventListener
  public void handleTenantDeleteEvent(TenantDeleteEvent tenantDeleteEvent) {
    log.debug("handleTenantDeleteEvent: {}", tenantDeleteEvent.getTenant());
    Tenant tenant = tenantDeleteEvent.getTenant();
    BasicDataSource dataSource = (BasicDataSource)tenantDataSources.getIfPresent(tenant.getId());
    if (dataSource != null) {
      try {
        dataSource.close();
        log.info("Closed datasource on delete tenant(url:[{}]).", dataSource.getUrl());
      } catch (SQLException e) {
        log.warn(e.toString());
      }
    }

    tenantDatabaseHelper.dropDatabase(tenant);
  }


}