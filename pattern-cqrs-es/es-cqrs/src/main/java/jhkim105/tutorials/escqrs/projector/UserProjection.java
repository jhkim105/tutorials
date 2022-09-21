package jhkim105.tutorials.escqrs.projector;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.escqrs.domain.User;
import jhkim105.tutorials.escqrs.query.UserByUsernameQuery;
import jhkim105.tutorials.escqrs.repository.UserReadRepository;
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
