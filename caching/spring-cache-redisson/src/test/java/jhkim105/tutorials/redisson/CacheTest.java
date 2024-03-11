package jhkim105.tutorials.redisson;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import jhkim105.tutorials.redisson.service.CurrentDateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class CacheTest {

  @Autowired
  CurrentDateService currentDateService;

  @Autowired
  RedisTemplate redisTemplate;

  @Autowired
  RedisCacheManager redisCacheManager;

  @Test
  void test() {
    String pattern = "yyyy-MM-dd hh:MM:SSS";
    var currentDate = currentDateService.getCurrentDate(pattern);
    var currentDate2 = currentDateService.getCurrentDate(pattern);
    assertThat(currentDate.getDate()).isEqualTo(currentDate2.getDate());

    currentDateService.evictCache();
    currentDate2 = currentDateService.getCurrentDate(pattern);
    assertThat(currentDate.getDate()).isNotEqualTo(currentDate2.getDate());

    redisCacheManager.getCache(Caches.CURRENT_DATE).clear();
    currentDate2 = currentDateService.getCurrentDate(pattern);
    assertThat(currentDate.getDate()).isNotEqualTo(currentDate2.getDate());

  }


}
