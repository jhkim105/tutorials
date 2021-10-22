package jhkim105.tutorials.replication.driver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomApplicationRunner implements ApplicationRunner {

  private final BasicDataSource dataSource;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("maxWaitMillis:{}", dataSource.getMaxWaitMillis());
    log.info("maxTotal:{}", dataSource.getMaxTotal());
    log.info("initialSize:{}", dataSource.getInitialSize());
    log.info("maxIdle:{}", dataSource.getMaxIdle());
    log.info("minIdle:{}", dataSource.getMinIdle());
    log.info("minEvictableIdleTimeMillis:{}", dataSource.getMinEvictableIdleTimeMillis());
    log.info("timeBetweenEvictionRunsMillis:{}", dataSource.getTimeBetweenEvictionRunsMillis());
    log.info("testOnBorrow:{}", dataSource.getTestOnBorrow());
    log.info("testWhileIdle:{}", dataSource.getTestWhileIdle());
  }
}
