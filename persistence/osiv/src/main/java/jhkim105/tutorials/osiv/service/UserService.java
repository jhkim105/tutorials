package jhkim105.tutorials.osiv.service;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.osiv.domain.User;
import jhkim105.tutorials.osiv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

  private final UserRepository repository;

  public User get(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Transactional
  public User getTransactional(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public User getUsingEntityGraph(String id) {
    return repository.getWithRolesById(id);
  }

  public User getUsingFetchJoin(String id) {
    return repository.getUsingFetchJoin(id);
  }

  @Transactional
  public User create(String username) {
    User user = new User(username);
    return repository.save(user);
  }

}
