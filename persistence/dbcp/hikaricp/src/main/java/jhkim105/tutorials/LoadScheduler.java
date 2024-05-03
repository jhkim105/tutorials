package jhkim105.tutorials;

import java.util.stream.IntStream;
import jhkim105.tutorials.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LoadScheduler {

  private final UserService userService;

  @Scheduled(cron = "0/2 * * * * *")
  public void getUsers() {
    IntStream.range(0, 30).parallel().forEach( i ->
        userService.getUsers()
    );
  }


}
