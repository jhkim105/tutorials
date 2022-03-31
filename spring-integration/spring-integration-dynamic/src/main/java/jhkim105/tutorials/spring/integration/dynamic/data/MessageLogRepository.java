package jhkim105.tutorials.spring.integration.dynamic.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, String> {

}
