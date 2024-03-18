package jhkim105.tutorials.config;


import jakarta.persistence.EntityManagerFactory;
import jhkim105.tutorials.domain.GroupListener;
import jhkim105.tutorials.domain.UserListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaListenerConfig implements InitializingBean {

  private final EntityManagerFactory entityManagerFactory;
  private final GroupListener groupListener;
  private final UserListener userListener;


  @Override
  public void afterPropertiesSet() throws Exception {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry eventListenerRegistry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);

    eventListenerRegistry.appendListeners(EventType.PRE_INSERT, groupListener);
    eventListenerRegistry.appendListeners(EventType.POST_INSERT, groupListener);
    eventListenerRegistry.appendListeners(EventType.PRE_INSERT, userListener);
    eventListenerRegistry.appendListeners(EventType.POST_INSERT, userListener);
  }

}
