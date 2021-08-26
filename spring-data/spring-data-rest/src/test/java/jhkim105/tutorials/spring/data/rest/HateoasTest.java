package jhkim105.tutorials.spring.data.rest;

import jhkim105.tutorials.spring.data.rest.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;

@SpringBootTest
@Slf4j
public class HateoasTest {

  @Autowired
  RepositoryEntityLinks repositoryEntityLinks;

  @Test
  void link() {
    log.debug("{}", repositoryEntityLinks.linkFor(User.class).slash("id01").toUri());
  }
}
