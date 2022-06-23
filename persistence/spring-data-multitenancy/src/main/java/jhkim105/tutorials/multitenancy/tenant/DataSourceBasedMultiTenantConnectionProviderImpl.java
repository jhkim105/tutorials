package jhkim105.tutorials.multitenancy.tenant;

import static jhkim105.tutorials.multitenancy.master.domain.Tenant.DEFAULT_TENANT_ID;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private static final long serialVersionUID = -6837240859738111430L;

  private TenantRepository tenantRepository;

  private BasicDataSource masterDataSource;

  private Map<String, DataSource> dataSourceMap = new HashMap<>();

  @Autowired
  public void setTenantRepository(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  @Resource(name = "masterDataSource")
  public void setBasicDataSource(BasicDataSource masterDataSource) {
    this.masterDataSource = masterDataSource;
  }

  @Override
  protected DataSource selectAnyDataSource() {
    log.info("selectAnyDataSource");
    return masterDataSource;
  }


  @Override
  protected DataSource selectDataSource(String tenantId) {
    if (DEFAULT_TENANT_ID.equals(tenantId)) {
      log.info("MasterDataSource used");
      return masterDataSource;
    }
    DataSource dataSource = dataSourceMap.get(tenantId);
    if (dataSource == null) {
      Tenant tenant = tenantRepository.findById(tenantId).orElseThrow();
      dataSource = createDataSource(tenant);;
      dataSourceMap.put(tenantId, dataSource);
    }

    return dataSource;
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
    tenantDataSource.setTimeBetweenEvictionRunsMillis(masterDataSource.getTimeBetweenEvictionRunsMillis());
    tenantDataSource.setMinEvictableIdleTimeMillis(masterDataSource.getMinEvictableIdleTimeMillis());
    tenantDataSource.setDefaultAutoCommit(masterDataSource.getDefaultAutoCommit());
    log.debug("tenantDataSource created. url:{}, maxTotal:{}, maxIdle:{}, minIdle:{}",
        tenantDataSource.getUrl(), tenantDataSource.getMaxTotal(), tenantDataSource.getMaxIdle(), tenantDataSource.getMinIdle());
    return tenantDataSource;
  }

}