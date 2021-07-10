package com.example.demo.domain.listener;

import com.example.demo.domain.base.BaseEntity;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Slf4j
public class JpaEventListener {

  protected void saveEntity(Session eventSession, BaseEntity<?> entity) {
    log.trace("JpaEventListener saveEntity({}, {}) start..", entity.getClass().getName(), entity.getId());

    Session session = openSession(eventSession);
    try {
      saveEntityInTransaction(entity, session);
    } catch (Exception ex) {
      // ignored
      log.error(String.format("JpaEventListener saveEntity(%s, %s) Error::%s", entity.getClass().getName(), entity.getId(), ex.getMessage()), ex);
    } finally {
      closeSessionQuietly(session);
    }

    log.trace("JpaEventListener saveEntity({}, {}) end..", entity.getClass().getName(), entity.getId());
  }

  protected void mergeEntity(Session eventSession, BaseEntity<?> entity) {
    log.trace("JpaEventListener mergeEntity({}, {}) start..", entity.getClass().getName(), entity.getId());

    Session session = openSession(eventSession);
    try {
      mergeEntity(entity, session);
    } catch (Exception ex) {
      // ignored
      log.error(String.format("JpaEventListener saveEntity(%s, %s) Error::%s", entity.getClass().getName(), entity.getId(), ex.getMessage()), ex);
    } finally {
      closeSessionQuietly(session);
    }

    log.trace("JpaEventListener mergeEntity({}, {}) end..", entity.getClass().getName(), entity.getId());
  }

  protected Object getEntity(Session eventSession, Class<?> clazz, Serializable id) {
    Session session = openSession(eventSession);
    Object entity = null;
    try {
      entity = session.get(clazz, id);
    } catch (Exception ex) {
      // ignored
      log.error(String.format("JpaEventListener getEntity(%s, %s) Error::%s", clazz.getName(), String.valueOf(id), ex.getMessage()), ex);
    } finally {
      closeSessionQuietly(session);
    }
    return entity;
  }

  private void saveEntityInTransaction(BaseEntity<?> entity, Session session) {
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(entity);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      throw ex;
    }
  }

  private void mergeEntity(BaseEntity<?> entity, Session session) {
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.merge(entity);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      throw ex;
    }
  }

  private Session openSession(Session eventSession) {
    return eventSession.getSessionFactory().openSession();
  }

  private void closeSessionQuietly(Session session) {
    try {
      if (session.isOpen()) {
        session.close();
      }
    } catch (Exception e) {
      // ignored
    }
  }

}
