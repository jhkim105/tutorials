
## Setup
- Event Listener 구현
```java
@Slf4j
@Component
public class GroupListener implements PreInsertEventListener, PostInsertEventListener {

  @Override
  public boolean onPreInsert(PreInsertEvent event) {
    final Object entity = event.getEntity();
    if (!(entity instanceof Group)) {
      return false;
    }
    log.info(">> GroupListener.onPreInsert: [{}]", event.getEntity());
    log.info("currentTx: {}", TransactionSynchronizationManager.getCurrentTransactionName());

    return false;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    final Object entity = event.getEntity();
    if (!(entity instanceof Group)) {
      return;
    }
    log.info(">> GroupListener.onPostInsert: [{}]", event.getEntity());
    createLogOnPostCommitSuccess(event);
  }

  private void createLogOnPostCommitSuccess(PostInsertEvent event) {
    event.getSession().getActionQueue().registerProcess((success, sessionImplementor) -> {
      if (success) {
        log.info(">> GroupListener.onPostInsert PostCommit success: [{}]", event.getEntity());
      }
    });
  }
  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return false; 
  }

}
```

- Event Listener 등록
```java
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
```
requiresPostCommitHandling() true/false 관계없이 동일하게 동작함

## Log
```shell
2024-03-18T11:19:32.534+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] jhkim105.tutorials.GroupTest             : > group create start
2024-03-18T11:19:32.537+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2024-03-18T11:19:32.537+09:00 TRACE 77728 --- [jpa-event-listener] [           main] .i.SessionFactoryImpl$SessionBuilderImpl : Opening Hibernate Session.  tenant=null
2024-03-18T11:19:32.537+09:00 TRACE 77728 --- [jpa-event-listener] [           main] org.hibernate.internal.SessionImpl       : Opened Session [f2eddf2f-5011-4c28-9322-13995bb9d437] at timestamp: 1710728372537
2024-03-18T11:19:32.540+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Opened new EntityManager [SessionImpl(1241568657PersistenceContext[entityKeys=[], collectionKeys=[]];ActionQueue[insertions=ExecutableList{size=0} updates=ExecutableList{size=0} deletions=ExecutableList{size=0} orphanRemovals=ExecutableList{size=0} collectionCreations=ExecutableList{size=0} collectionRemovals=ExecutableList{size=0} collectionUpdates=ExecutableList{size=0} collectionQueuedOps=ExecutableList{size=0} unresolvedInsertDependencies=null])] for JPA transaction
2024-03-18T11:19:32.544+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Exposing JPA transaction as JDBC [org.springframework.orm.jpa.vendor.HibernateJpaDialect$HibernateConnectionHandle@6fd07a56]
2024-03-18T11:19:32.545+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2024-03-18T11:19:32.547+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.hibernate.event.internal.EntityState   : Transient instance of: jhkim105.tutorials.domain.Group
2024-03-18T11:19:32.547+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.DefaultPersistEventListener      : Saving transient instance
2024-03-18T11:19:32.547+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractSaveEventListener        : Generated identifier: 05715003-27f1-48d0-a988-6be383a4434b, using strategy: org.hibernate.id.uuid.UuidGenerator
2024-03-18T11:19:32.556+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] jhkim105.tutorials.domain.Group          : >> Group.prePersist
2024-03-18T11:19:32.556+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractSaveEventListener        : Saving [jhkim105.tutorials.domain.Group#05715003-27f1-48d0-a988-6be383a4434b]
2024-03-18T11:19:32.561+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.hibernate.event.internal.WrapVisitor   : Wrapped collection in role: jhkim105.tutorials.domain.Group.users
2024-03-18T11:19:32.563+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2024-03-18T11:19:32.563+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
2024-03-18T11:19:32.564+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1241568657PersistenceContext[entityKeys=[EntityKey[jhkim105.tutorials.domain.Group#05715003-27f1-48d0-a988-6be383a4434b]], collectionKeys=[]];ActionQueue[insertions=ExecutableList{size=1} updates=ExecutableList{size=0} deletions=ExecutableList{size=0} orphanRemovals=ExecutableList{size=0} collectionCreations=ExecutableList{size=0} collectionRemovals=ExecutableList{size=0} collectionUpdates=ExecutableList{size=0} collectionQueuedOps=ExecutableList{size=0} unresolvedInsertDependencies=null])]
2024-03-18T11:19:32.564+09:00 TRACE 77728 --- [jpa-event-listener] [           main] org.hibernate.internal.SessionImpl       : SessionImpl#beforeTransactionCompletion()
2024-03-18T11:19:32.564+09:00 TRACE 77728 --- [jpa-event-listener] [           main] org.hibernate.internal.SessionImpl       : Automatically flushing session
2024-03-18T11:19:32.564+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Flushing session
2024-03-18T11:19:32.564+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
2024-03-18T11:19:32.564+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
2024-03-18T11:19:32.564+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Flushing entities and processing referenced collections
2024-03-18T11:19:32.565+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Processing unreferenced collections
2024-03-18T11:19:32.565+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Scheduling collection removes/(re)creates/updates
2024-03-18T11:19:32.566+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 1 insertions, 0 updates, 0 deletions to 1 objects
2024-03-18T11:19:32.566+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 1 (re)creations, 0 updates, 0 removals to 1 collections
2024-03-18T11:19:32.567+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
2024-03-18T11:19:32.567+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.hibernate.internal.util.EntityPrinter  : jhkim105.tutorials.domain.Group{createdDt=2024-03-18T02:19:32.552603Z, name=group 111, description=null, id=05715003-27f1-48d0-a988-6be383a4434b, users=[]}
2024-03-18T11:19:32.567+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Executing flush
2024-03-18T11:19:32.567+09:00  INFO 77728 --- [jpa-event-listener] [           main] j.tutorials.domain.GroupListener         : >> GroupListener.onPreInsert: [Group(id=05715003-27f1-48d0-a988-6be383a4434b, name=group 111, description=null, createdDt=2024-03-18T02:19:32.552603Z)]
2024-03-18T11:19:32.568+09:00  INFO 77728 --- [jpa-event-listener] [           main] j.tutorials.domain.GroupListener         : currentTx: org.springframework.data.jpa.repository.support.SimpleJpaRepository.save
2024-03-18T11:19:32.570+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] org.hibernate.SQL                        : insert into t_group (created_dt,description,name,id) values (?,?,?,?)
2024-03-18T11:19:32.580+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] jhkim105.tutorials.domain.Group          : >> Group.postPersist
2024-03-18T11:19:32.580+09:00  INFO 77728 --- [jpa-event-listener] [           main] j.tutorials.domain.GroupListener         : >> GroupListener.onPostInsert: [Group(id=05715003-27f1-48d0-a988-6be383a4434b, name=group 111, description=null, createdDt=2024-03-18T02:19:32.552603Z)]
2024-03-18T11:19:32.581+09:00 TRACE 77728 --- [jpa-event-listener] [           main] o.h.e.i.AbstractFlushingEventListener    : Post flush
2024-03-18T11:19:32.591+09:00 TRACE 77728 --- [jpa-event-listener] [           main] org.hibernate.internal.SessionImpl       : SessionImpl#afterTransactionCompletion(successful=true, delayed=false)
2024-03-18T11:19:32.591+09:00  INFO 77728 --- [jpa-event-listener] [           main] j.tutorials.domain.GroupListener         : >> GroupListener.onPostInsert PostCommit success: [Group(id=05715003-27f1-48d0-a988-6be383a4434b, name=group 111, description=null, createdDt=2024-03-18T02:19:32.552603Z)]
2024-03-18T11:19:32.591+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] o.s.orm.jpa.JpaTransactionManager        : Closing JPA EntityManager [SessionImpl(1241568657PersistenceContext[entityKeys=[EntityKey[jhkim105.tutorials.domain.Group#05715003-27f1-48d0-a988-6be383a4434b]], collectionKeys=[CollectionKey[jhkim105.tutorials.domain.Group.users#05715003-27f1-48d0-a988-6be383a4434b]]];ActionQueue[insertions=ExecutableList{size=0} updates=ExecutableList{size=0} deletions=ExecutableList{size=0} orphanRemovals=ExecutableList{size=0} collectionCreations=ExecutableList{size=0} collectionRemovals=ExecutableList{size=0} collectionUpdates=ExecutableList{size=0} collectionQueuedOps=ExecutableList{size=0} unresolvedInsertDependencies=null])] after transaction
2024-03-18T11:19:32.591+09:00 TRACE 77728 --- [jpa-event-listener] [           main] org.hibernate.internal.SessionImpl       : Closing session [f2eddf2f-5011-4c28-9322-13995bb9d437]
2024-03-18T11:19:32.592+09:00 DEBUG 77728 --- [jpa-event-listener] [           main] jhkim105.tutorials.GroupTest             : > group create end
```
