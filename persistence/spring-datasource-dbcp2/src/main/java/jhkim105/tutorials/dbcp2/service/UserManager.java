package jhkim105.tutorials.dbcp2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jhkim105.tutorials.dbcp2.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager {

  @PersistenceContext
  EntityManager em;

  public List<User> findAllLeakCaused() {
    return em.getEntityManagerFactory().createEntityManager().createQuery("select u from User u").getResultList();
  }


  @Transactional(readOnly = true)
  public void doInReadOnlyTransaction() {
    try {
      TimeUnit.SECONDS.sleep(30);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
