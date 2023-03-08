package jhkim105.tutorials.multitenancy.master;


import javax.persistence.EntityManagerFactory;
import jhkim105.tutorials.multitenancy.master.domain.TenantListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MasterJpaListenerConfig implements InitializingBean {

  private final TenantListener tenantListener;
  private final EntityManagerFactory entityManagerFactory;

  @Override
  public void afterPropertiesSet() throws Exception {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry eventListenerRegistry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

    eventListenerRegistry.appendListeners(EventType.POST_DELETE, tenantListener);
  }
}
