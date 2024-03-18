package jhkim105.tutorials;


import jhkim105.tutorials.domain.Group;
import jhkim105.tutorials.domain.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GroupTest {

  @Autowired
  GroupRepository groupRepository;



  @Test
  void create() {
    log.debug("> group create start");
    Group group = new Group("group 111");
    groupRepository.save(group);
    log.debug("> group create end");

  }

}
