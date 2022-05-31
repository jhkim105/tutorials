package com.example.multitenant.tenant;

import com.example.multitenant.master.domain.Tenant;
import com.example.multitenant.master.repository.TenantRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
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

  private DataSource createDataSource(Tenant source) {
    BasicDataSource tenantDataSource = new BasicDataSource();
    tenantDataSource.setUsername(source.getDbUsername());
    tenantDataSource.setPassword(source.getDbPassword());
    tenantDataSource.setUrl(source.getJdbcUrl());

    tenantDataSource.setMaxTotal(source.getMaxTotal());
    tenantDataSource.setMaxIdle(source.getMaxIdle());
    tenantDataSource.setMinIdle(source.getMinIdle());
    tenantDataSource.setInitialSize(source.getInitialSize());
    tenantDataSource.setTimeBetweenEvictionRunsMillis(masterDataSource.getTimeBetweenEvictionRunsMillis());
    tenantDataSource.setMinEvictableIdleTimeMillis(masterDataSource.getMinEvictableIdleTimeMillis());
    tenantDataSource.setDefaultAutoCommit(masterDataSource.getDefaultAutoCommit());
    log.debug("tenantDataSource created. url:{}, maxTotal:{}, maxIdle:{}, minIdle:{}",
        tenantDataSource.getUrl(), tenantDataSource.getMaxTotal(), tenantDataSource.getMaxIdle(), tenantDataSource.getMinIdle());
    return tenantDataSource;
  }

  @Override
  protected DataSource selectDataSource(String tenantId) {
    DataSource dataSource = dataSourceMap.get(tenantId);
    if (dataSource == null) {
      Tenant tenantDataSource = tenantRepository.findById(tenantId).orElseThrow();
      dataSource = createDataSource(tenantDataSource);;
      dataSourceMap.put(tenantId, dataSource);
    }

    return dataSource;
  }
}