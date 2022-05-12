package jhkim105.tutorials.spring.data.jpa.reposiotry;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.domain.User;

public interface UserRepositoryCustom {
  List<User> getUsers();
}
