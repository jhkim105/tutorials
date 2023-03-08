package jhkim105.tutorials.multitenancy.master.domain;

import javax.persistence.PostRemove;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantListener implements PostDeleteEventListener {

  private static final long serialVersionUID = 3930521455885103861L;

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void onPostDelete(PostDeleteEvent event) {
    if (!(event.getEntity() instanceof Tenant)) {
      return;
    }

    event.getSession().getActionQueue().registerProcess((success, sessionImplementor) -> {
      if (success) {
        Tenant tenant = (Tenant) event.getEntity();
        eventPublisher.publishEvent(new TenantDeleteEvent(this, tenant));
      }
    });
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return true;
  }

}
