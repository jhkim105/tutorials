package jhkim105.tutorials.service;

import jhkim105.tutorials.domain.user.User;
import jhkim105.tutorials.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManager extends GenericManager<User, String> {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    super.repository = userRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public User getById(String id) {
    return userRepository.getById(id);
  }

}
