package jhkim105.tutorials.cqrs.aggregate;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.cqrs.command.CreateUserCommand;
import jhkim105.tutorials.cqrs.command.UpdateUserCommand;
import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.repository.UserWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAggregate {

  private final UserWriteRepository writeRepository;

  public User handleCreateUserCommand(CreateUserCommand command) {
    User user = User.builder()
        .name(command.getName())
        .build();
    return writeRepository.save(user);
  }

  public User handleUpdateUserCommand(UpdateUserCommand command) {
    User user = writeRepository.findById(command.getId())
        .orElseThrow(EntityNotFoundException::new);
    user.setAddresses(command.getAddresses());
    return writeRepository.save(user);
  }

}
