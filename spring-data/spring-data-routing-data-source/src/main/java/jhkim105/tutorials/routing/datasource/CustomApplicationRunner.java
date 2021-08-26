package jhkim105.tutorials.routing.datasource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomApplicationRunner implements ApplicationRunner {

  @Resource(name = "masterDataSource")
  private DataSource masterDataSource;

  @Resource(name = "slaveDataSource")
  private DataSource slaveDataSource;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    BasicDataSource master = (BasicDataSource)masterDataSource;
    BasicDataSource slave = (BasicDataSource)slaveDataSource;

    log.info("master.url:{}", master.getUrl());
    log.info("master.maxWaitMillis:{}", master.getMaxWaitMillis());
    log.info("master.maxTotal:{}", master.getMaxTotal());
    log.info("master.initialSize:{}", master.getInitialSize());
    log.info("master.maxIdle:{}", master.getMaxIdle());
    log.info("master.minIdle:{}", master.getMinIdle());
    log.info("master.minEvictableIdleTimeMillis:{}", master.getMinEvictableIdleTimeMillis());
    log.info("master.timeBetweenEvictionRunsMillis:{}", master.getTimeBetweenEvictionRunsMillis());
    log.info("master.testOnBorrow:{}", master.getTestOnBorrow());
    log.info("master.testWhileIdle:{}", master.getTestWhileIdle());

    log.info("slave.url:{}", slave.getUrl());
    log.info("slave.maxWaitMillis:{}", slave.getMaxWaitMillis());
    log.info("slave.maxTotal:{}", slave.getMaxTotal());
    log.info("slave.initialSize:{}", slave.getInitialSize());
    log.info("slave.maxIdle:{}", slave.getMaxIdle());
    log.info("slave.minIdle:{}", slave.getMinIdle());
    log.info("slave.minEvictableIdleTimeMillis:{}", slave.getMinEvictableIdleTimeMillis());
    log.info("slave.timeBetweenEvictionRunsMillis:{}", slave.getTimeBetweenEvictionRunsMillis());
    log.info("slave.testOnBorrow:{}", slave.getTestOnBorrow());
    log.info("slave.testWhileIdle:{}", slave.getTestWhileIdle());
  }
}
