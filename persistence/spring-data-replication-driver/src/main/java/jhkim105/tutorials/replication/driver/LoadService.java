package jhkim105.tutorials.replication.driver;

import java.util.stream.IntStream;
import jhkim105.tutorials.replication.driver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadService {


  private final UserService userService;

  @Async
  public void load(int no) {
    IntStream.range(0, no).parallel().forEach( i -> {
      userService.findAll();
    });
  }
}
