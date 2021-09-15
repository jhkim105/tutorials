package com.example.multitenant.master;

import javax.annotation.Resource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

  private static final long serialVersionUID = -6837240859738111430L;

  private TenantRepository tenantRepository;

  private BasicDataSource masterDataSource;

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
    return masterDataSource;
  }

  private DataSource createDataSource(Tenant source) {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUsername(source.getDbUsername());
    dataSource.setPassword(source.getDbPassword());
    dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
    dataSource.setUrl(source.getJdbcUrl());
    return dataSource;
  }

  @Override
  protected DataSource selectDataSource(String tenantId) {
    Tenant tenantDataSource = tenantRepository.findById(tenantId).orElseThrow();
    return createDataSource(tenantDataSource);
  }
}
