package jhkim105.tutorials.concurrency.database;

import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NoticeRepositoryTest {

  @Autowired
  NoticeRepository repository;

  @Autowired
  EntityManager em;

  private String noticeId;

  @BeforeEach
  void beforeEach() {
    Notice notice = Notice.builder().title("test").build();
    repository.save(notice);
    noticeId = notice.getId();
  }

  @Test
  void increaseCount() {
    repository.increaseCount(noticeId);
//    em.clear();

    Notice notice = repository.findById(noticeId).get();
    Assertions.assertThat(notice.getCount()).isEqualTo(1);
  }

}