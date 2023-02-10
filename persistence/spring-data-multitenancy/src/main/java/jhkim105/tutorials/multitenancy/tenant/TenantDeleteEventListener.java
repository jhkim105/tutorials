package jhkim105.tutorials.multitenancy.tenant;

import java.sql.SQLException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.domain.TenantDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TenantDeleteEventListener {

  private final TenantDataSources tenantDataSources;
  private final TenantDatabaseHelper tenantDatabaseHelper;

  @EventListener
  public void handleTenantDeleteEvent(TenantDeleteEvent tenantDeleteEvent) {
    Tenant tenant = tenantDeleteEvent.getTenant();
    log.debug("handleTenantDeleteEvent: {}", tenant);

    closeDataSource(tenant);
    tenantDatabaseHelper.dropDatabase(tenant);
  }

  private void closeDataSource(Tenant tenant) {
    BasicDataSource dataSource = (BasicDataSource) tenantDataSources.getIfPresent(tenant.getId());
    if (dataSource != null) {
      try {
        dataSource.close();
        log.info("Closed datasource on delete tenant(url:[{}]).", dataSource.getUrl());
      } catch (SQLException e) {
        log.warn(e.toString());
      }
    }
  }

}
