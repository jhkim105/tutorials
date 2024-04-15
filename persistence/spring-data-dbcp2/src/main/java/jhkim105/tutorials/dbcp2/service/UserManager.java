package jhkim105.tutorials.dbcp2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jhkim105.tutorials.dbcp2.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager {

  @PersistenceContext
  EntityManager em;

  public List<User> findAllLeakCaused() {
    return em.getEntityManagerFactory().createEntityManager().createQuery("select u from User u").getResultList();
  }

}
