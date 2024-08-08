package jhkim105.tutorials;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserClientTest {

    @Autowired
    UserClient userClient;


    @Test
    void getAll() {
        log.debug("{}", userClient.getAll());
    }


    @Test
    void getOne() {
        log.debug("{}", userClient.getOne("id01"));
    }

}