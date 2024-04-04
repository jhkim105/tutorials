package jhkim105.tutorials;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class FcmTest {

  @Autowired
  FirebaseMessaging fcm;



  @Test
  void subscribe() throws FirebaseMessagingException {
    var tokens = List.of("aaa");
    var topic = "test_topic";
    var response = fcm.subscribeToTopic(tokens, topic);
    log.info("{}", response.getSuccessCount());
  }


}
