package com.example.dbcp2;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@ConditionalOnProperty(name = "scheduled.data-source-monitoring.cron")
public class DataSourceMonitoring {

  private BasicDataSource basicDataSource;

  @Autowired
  public void setBasicDataSource(BasicDataSource basicDataSource) {
    this.basicDataSource = basicDataSource;
    log.info("url:{}, maxWaitMillis:{}, maxTotal:{}, initialSize:{}, maxIdle:{}, minIdle:{}, timeBetweenEvictionRunsMillis:{}",
        basicDataSource.getUrl(), basicDataSource.getMaxWaitMillis(), basicDataSource.getMaxTotal(), basicDataSource.getInitialSize(),
        basicDataSource.getMaxIdle(), basicDataSource.getMinIdle(), basicDataSource.getTimeBetweenEvictionRunsMillis());
  }

  @Scheduled(cron = "${scheduled.data-source-monitoring.cron}")
  public void doLogging() {
    DataSourceInfo info = DataSourceInfo.from(basicDataSource);
    log.info("{}", info);
  }


  @ToString
  static class DataSourceInfo {
    private int numActive;
    private int numIdle;

    static DataSourceInfo from(BasicDataSource dataSource) {
      DataSourceInfo vo = new DataSourceInfo();
      vo.numActive = dataSource.getNumActive();
      vo.numIdle = dataSource.getNumIdle();
      return vo;
    }
  }

}
