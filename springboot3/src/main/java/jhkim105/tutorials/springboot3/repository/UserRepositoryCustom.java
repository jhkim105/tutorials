package jhkim105.tutorials.springboot3.repository;

import java.util.List;
import jhkim105.tutorials.springboot3.domain.User;

public interface UserRepositoryCustom {
  List<User> getUsers();
}
