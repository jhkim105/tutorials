package jhkim105.tutorials.redis;


import jhkim105.tutorials.redis.service.CountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CountServiceTest {

    @Autowired
    private CountService countService;

    @Test
    void testCacheExpiration() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long endTime = System.currentTimeMillis() + 5_000;

        while (System.currentTimeMillis() < endTime) {
            executorService.submit(() -> {
                int count = countService.getCount("Test");
                System.out.println(count);
            });
            Thread.sleep(200);
        }

        executorService.shutdown();
        executorService.awaitTermination(15, TimeUnit.SECONDS);

        int executionCount = countService.getExecutionCount();
        System.out.println("executionCount: " + executionCount);
        assertThat(executionCount).isLessThan(50);
    }
}
