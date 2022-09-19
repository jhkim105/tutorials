package jhkim105.tutorials.cqrs.repository;

import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.domain.UserAddress;

public interface UserReadRepository extends ReadOnlyRepository<User, String> {

  UserAddress getUserAddress(String userId);
}
