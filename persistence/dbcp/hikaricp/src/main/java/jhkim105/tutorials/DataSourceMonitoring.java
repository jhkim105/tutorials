package jhkim105.tutorials;

import com.zaxxer.hikari.HikariDataSource;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSourceMonitoring {

    private HikariDataSource hikariDataSource;

    @Autowired
    public void setHikariDataSource(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
        log.info("JDBC URL: {}, Maximum Pool Size: {}, Minimum Idle: {}, Idle Timeout: {}ms",
                hikariDataSource.getJdbcUrl(), hikariDataSource.getMaximumPoolSize(),
                hikariDataSource.getMinimumIdle(), hikariDataSource.getIdleTimeout());
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void doLogging() {
        DataSourceInfo info = DataSourceInfo.from(hikariDataSource);
        log.info("{}", info);
    }

    @ToString
    static class DataSourceInfo {
        private int total;
        private int numActive;
        private int numIdle;

        static DataSourceInfo from(HikariDataSource dataSource) {
            DataSourceInfo info = new DataSourceInfo();
            info.numActive = dataSource.getHikariPoolMXBean().getActiveConnections();
            info.numIdle = dataSource.getHikariPoolMXBean().getIdleConnections();
            info.total = info.numActive + info.numIdle;
            return info;
        }
    }
}
