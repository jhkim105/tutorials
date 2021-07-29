package com.example.demo.domain.listener;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JpaEventListenerIntegrator implements Integrator {

  @Override
  public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JpaEventListenerConfig.class);
    eventListenerRegistry.appendListeners(EventType.POST_INSERT, new OrderLogWriter(ctx));
    eventListenerRegistry.appendListeners(EventType.POST_UPDATE, new OrderLogWriter(ctx));
    eventListenerRegistry.appendListeners(EventType.POST_DELETE, new OrderLogWriter(ctx));
  }

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

  }
}
