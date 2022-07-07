package jhkim105.tutorials.multitenancy.tenant;

import static jhkim105.tutorials.multitenancy.master.domain.Tenant.DEFAULT_TENANT_ID;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private static final long serialVersionUID = 2353465673130594043L;

  private TenantRepository tenantRepository;

  private BasicDataSource dataSource;

  private Map<String, DataSource> dataSourceMap = new HashMap<>();

  @Autowired
  public void setTenantRepository(TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  @Resource(name = "dataSource")
  public void setBasicDataSource(BasicDataSource dataSource) {
    this.dataSource = dataSource;
  }

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
    DataSource dataSource = dataSourceMap.get(tenantId);
    if (dataSource == null) {
      Tenant tenant = tenantRepository.findById(tenantId).orElseThrow();
      dataSource = createDataSource(tenant);;
      dataSourceMap.put(tenantId, dataSource);
    }
    log.info("TenantDataSource selected. id: {}", tenantId);
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
    tenantDataSource.setTimeBetweenEvictionRunsMillis(dataSource.getTimeBetweenEvictionRunsMillis());
    tenantDataSource.setMinEvictableIdleTimeMillis(dataSource.getMinEvictableIdleTimeMillis());
    tenantDataSource.setDefaultAutoCommit(dataSource.getDefaultAutoCommit());
    log.debug("TenantDataSource created. id: {}, url: {}, maxTotal: {}, maxIdle: {}, minIdle: {}",
        tenant.getId(),
        tenantDataSource.getUrl(), tenantDataSource.getMaxTotal(), tenantDataSource.getMaxIdle(), tenantDataSource.getMinIdle());
    return tenantDataSource;
  }

}