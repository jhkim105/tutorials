package jhkim105.tutorials.spring.data.jpa.service;

import jhkim105.tutorials.spring.data.jpa.domain.User;
import jhkim105.tutorials.spring.data.jpa.reposiotry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager extends GenericManager<User, String> {

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    super.repository = userRepository;
  }
}
