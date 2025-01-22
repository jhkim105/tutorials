package jhkim105.tutorials.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import static jhkim105.tutorials.redis.config.CacheConfig.Caches.COUNT;

@Service
@Slf4j
public class CountService {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Cacheable(value = COUNT, key = "#name", sync = true)
    public int getCount(String name) {
        int count = counter.incrementAndGet();
        log.info("count for {}: {}", name, count);
        return count;
    }

    public int getExecutionCount() {
        return counter.get();
    }
}
