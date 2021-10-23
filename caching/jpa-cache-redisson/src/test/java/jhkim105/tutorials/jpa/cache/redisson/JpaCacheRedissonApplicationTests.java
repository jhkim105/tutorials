package jhkim105.tutorials.jpa.cache.redisson;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaCacheRedissonApplicationTests {


    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void testCache() throws InterruptedException {
        userRepository.findById(-99L);
        userRepository.findById(-99L);
        userRepository.findById(-99L);
        Thread.sleep(2000l);
        userRepository.findById(-99L);
  }

    @Test
    void testNonCache() {
        orderRepository.findById(-99L);
        orderRepository.findById(-99L);
        orderRepository.findById(-99L);
        orderRepository.findById(-99L);
    }
}
