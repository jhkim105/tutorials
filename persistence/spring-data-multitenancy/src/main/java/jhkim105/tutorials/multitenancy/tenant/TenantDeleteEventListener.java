package jhkim105.tutorials.multitenancy.tenant;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.domain.TenantDeleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    tenantDataSources.invalidate(tenant.getId());
    tenantDatabaseHelper.dropDatabase(tenant);
  }

}
