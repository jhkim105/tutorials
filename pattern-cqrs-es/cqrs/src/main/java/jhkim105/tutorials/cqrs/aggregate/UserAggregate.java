package jhkim105.tutorials.cqrs.aggregate;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.cqrs.command.CreateUserCommand;
import jhkim105.tutorials.cqrs.command.DeleteUserCommand;
import jhkim105.tutorials.cqrs.command.UpdateUserCommand;
import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.repository.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAggregate {

  private final UserWriteRepository writeRepository;

  public User handle(CreateUserCommand command) {
    User user = User.builder()
        .username(command.getUsername())
        .build();
    return writeRepository.save(user);
  }

  public User handle(UpdateUserCommand command) {
    User user = writeRepository.findById(command.getId()).orElseThrow(EntityNotFoundException::new);
    user.setUsername(command.getUsername());
    return writeRepository.save(user);
  }

  public void handle(DeleteUserCommand command) {
    writeRepository.deleteById(command.getId());
  }
}
