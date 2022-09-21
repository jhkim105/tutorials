package jhkim105.tutorials.cqrs.projection;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.query.UserByUsernameQuery;
import jhkim105.tutorials.cqrs.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProjection {

  private final UserReadRepository readRepository;


  public User handle(UserByUsernameQuery query) {
    return readRepository.findByUsername(query.getUsername()).orElseThrow(EntityNotFoundException::new);
  }

}
