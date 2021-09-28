package jhkim105.tutorials.concurrency.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NoticeRepository extends JpaRepository<Notice, String> {

  @Query(nativeQuery = true, value = "update notice n set n.count = n.count + 1 where n.id = :id")
  @Transactional
  void increaseCount(@Param("id") String noticeId);
}
