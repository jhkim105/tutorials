package com.example.demo;

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

  @Resource(name = "userDataSource")
  private DataSource userDataSource;

  @Resource(name = "productDataSource")
  private DataSource productDataSource;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    BasicDataSource userDs = (BasicDataSource)userDataSource;
    BasicDataSource productDs = (BasicDataSource)productDataSource;

    log.info("userDs.maxWaitMillis:{}", userDs.getMaxWaitMillis());
    log.info("userDs.maxTotal:{}", userDs.getMaxTotal());
    log.info("userDs.initialSize:{}", userDs.getInitialSize());
    log.info("userDs.maxIdle:{}", userDs.getMaxIdle());
    log.info("userDs.minIdle:{}", userDs.getMinIdle());
    log.info("userDs.minEvictableIdleTimeMillis:{}", userDs.getMinEvictableIdleTimeMillis());
    log.info("userDs.timeBetweenEvictionRunsMillis:{}", userDs.getTimeBetweenEvictionRunsMillis());
    log.info("userDs.testOnBorrow:{}", userDs.getTestOnBorrow());
    log.info("userDs.testWhileIdle:{}", userDs.getTestWhileIdle());

    log.info("productDs.maxWaitMillis:{}", productDs.getMaxWaitMillis());
    log.info("productDs.maxTotal:{}", productDs.getMaxTotal());
    log.info("productDs.initialSize:{}", productDs.getInitialSize());
    log.info("productDs.maxIdle:{}", productDs.getMaxIdle());
    log.info("productDs.minIdle:{}", productDs.getMinIdle());
    log.info("productDs.minEvictableIdleTimeMillis:{}", productDs.getMinEvictableIdleTimeMillis());
    log.info("productDs.timeBetweenEvictionRunsMillis:{}", productDs.getTimeBetweenEvictionRunsMillis());
    log.info("productDs.testOnBorrow:{}", productDs.getTestOnBorrow());
    log.info("productDs.testWhileIdle:{}", productDs.getTestWhileIdle());
  }
}
