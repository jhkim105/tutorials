package jhkim105.tutorials.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;
import jhkim105.tutorials.domain.User;
import jhkim105.tutorials.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  @PersistenceContext
  EntityManager em;

  private final UserRepository userRepository;

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

  @Transactional(readOnly = true)
  public List<User> getUsers() {
    sleepSeconds(1);
    return userRepository.findAll();
  }

  private void sleepSeconds(int timeout) {
    try {
      TimeUnit.SECONDS.sleep(timeout);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
