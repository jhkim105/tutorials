package jhkim105.tutorials.multitenancy.master.domain;

import javax.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantListener implements PreDeleteEventListener {
  private final ApplicationEventPublisher eventPublisher;

  @PostRemove
  void publishDropDatabaseEvent(Tenant tenant) {
    eventPublisher.publishEvent(new TenantDeleteEvent(this, tenant));
  }

  @Override
  public boolean onPreDelete(PreDeleteEvent event) {
    if (!(event.getEntity() instanceof Tenant)) {
      return false;
    }
    Tenant tenant = (Tenant)event.getEntity();
    eventPublisher.publishEvent(new TenantDeleteEvent(this, tenant));
    return false;
  }

}
