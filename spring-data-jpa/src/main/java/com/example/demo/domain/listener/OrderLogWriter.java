package com.example.demo.domain.listener;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.EventSource;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class OrderLogWriter extends JpaEventListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

  public OrderLogWriter(AnnotationConfigApplicationContext ctx) {
  }

  @Override
  public void onPostDelete(PostDeleteEvent postDeleteEvent) {

  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    final Object entity = event.getEntity();
    log.debug("OrderLogWriter onPostInsert:{}", event.getEntity());

    final EventSource eventSession = event.getSession();
    eventSession.getActionQueue().registerProcess((success, sessionImplementor) -> {
      if (success) {
        log.debug("OrderLogWriter onPostInsert Success:{}", event.getEntity());;
      }
    });
  }

  @Override
  public void onPostUpdate(PostUpdateEvent postUpdateEvent) {

  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
    return true;
  }
}
