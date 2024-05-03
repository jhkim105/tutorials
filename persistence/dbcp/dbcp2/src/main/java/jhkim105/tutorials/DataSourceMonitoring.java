package jhkim105.tutorials;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataSourceMonitoring {

  private BasicDataSource basicDataSource;

  @Autowired
  public void setBasicDataSource(BasicDataSource basicDataSource) {
    this.basicDataSource = basicDataSource;
    log.info("url:{}, maxWaitMillis:{}, maxTotal:{}, initialSize:{}, maxIdle:{}, minIdle:{}, timeBetweenEvictionRunsMillis:{}",
        basicDataSource.getUrl(), basicDataSource.getMaxWaitDuration(), basicDataSource.getMaxTotal(), basicDataSource.getInitialSize(),
        basicDataSource.getMaxIdle(), basicDataSource.getMinIdle(), basicDataSource.getDurationBetweenEvictionRuns());
  }

  @Scheduled(cron = "0/5 * * * * *")
  public void doLogging() {
    DataSourceInfo info = DataSourceInfo.from(basicDataSource);
    log.info("{}", info);
  }


  @ToString
  static class DataSourceInfo {
    private int total;
    private int numActive;
    private int numIdle;

    static DataSourceInfo from(BasicDataSource dataSource) {
      DataSourceInfo info = new DataSourceInfo();
      info.numActive = dataSource.getNumActive();
      info.numIdle = dataSource.getNumIdle();
      info.total = info.numActive + info.numIdle;
      return info;
    }
  }

}
