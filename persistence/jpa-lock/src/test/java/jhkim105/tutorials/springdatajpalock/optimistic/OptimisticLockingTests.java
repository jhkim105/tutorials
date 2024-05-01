package jhkim105.tutorials.springdatajpalock.optimistic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
class OptimisticLockingTests {

  @PersistenceContext
  EntityManager em;

  @Autowired
  OptimisticRepository optimisticRepository;

//  @Transactional
  @Test
  void testVersionEntity_RollBackException() {
    EntityManager em1 = em.getEntityManagerFactory().createEntityManager();
    em1.getTransaction().begin();
    Optimistic optimistic1 = em1.find(Optimistic.class, "id01");

    EntityManager em2 = em.getEntityManagerFactory().createEntityManager();
    em2.getTransaction().begin();
    Optimistic optimistic2 = em2.find(Optimistic.class, "id01");
    optimistic2.incr();
    optimistic2 = em2.merge(optimistic2);
    log.info("{}", optimistic2);
    em2.getTransaction().commit();
    em2.close();

    optimistic1.incr();
    em1.merge(optimistic1);
    assertThrows(RollbackException.class, () -> em1.getTransaction().commit());
    em1.close();

  }

  @Test
  void testLock_RollBackException() {
    EntityManager em1 = em.getEntityManagerFactory().createEntityManager();
    em1.getTransaction().begin();
    Optimistic optimistic1 = em1.find(Optimistic.class, "id02", LockModeType.OPTIMISTIC);

    EntityManager em2 = em.getEntityManagerFactory().createEntityManager();
    em2.getTransaction().begin();
    Optimistic optimistic2 = em2.find(Optimistic.class, "id02", LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    em2.getTransaction().commit();
    log.info("{}", optimistic2);
    em2.close();

    optimistic1.incr();
    em1.merge(optimistic1);
    assertThrows(RollbackException.class, () -> em1.getTransaction().commit());
    em1.close();

  }


  @Test
  @Transactional
  void excludeOptimisticLock() {
    Phone phone = new Phone("010-1111-2222");
    em.persist(phone);
    em.flush();
    log.info("phone: {}", phone);

    Instant versionBefore = phone.getVersion();
    phone.setNumber("010-2222-3333");
    em.merge(phone);
    em.flush();
    assertThat(phone.getVersion()).isNotEqualTo(versionBefore);

    versionBefore = phone.getVersion();
    phone.setCallCount(1);
    em.merge(phone);
    em.flush();
    assertThat(phone.getVersion()).isEqualTo(versionBefore);
  }


  @Test
  void optimisticLockingTypeNone() {
    EntityManager em1 = em.getEntityManagerFactory().createEntityManager();
    em1.getTransaction().begin();
    OptimisticNone entity1 = em1.find(OptimisticNone.class, "id01");

    EntityManager em2 = em.getEntityManagerFactory().createEntityManager();
    em2.getTransaction().begin();
    OptimisticNone entity2 = em2.find(OptimisticNone.class, "id01");
    em2.getTransaction().commit();
    log.info("{}", entity2);
    em2.close();

    entity1.incr();
    em1.merge(entity1);
    em1.close();
  }
}
