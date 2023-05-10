package jhkim105.tutorials.flyway;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Disabled
@Slf4j
class FlywayTests {

  @Autowired
  private DataSource dataSource;

  @Test
  void migrate() {
    Flyway flyway = Flyway.configure()
        .dataSource(dataSource)
        .load();
    flyway.migrate();
  }


  @Test
  void info() {
    Flyway flyway = Flyway.configure()
        .dataSource(dataSource)
        .load();
    MigrationInfoService migrationInfoService = flyway.info();
    MigrationInfo[] migrationInfos = migrationInfoService.all();
    if (migrationInfos != null) {
      for (MigrationInfo migrationInfo : migrationInfos) {
        log.info(
            "Flyway: {}, script: {}, installed on: {}, state: {}"
            , migrationInfo.getVersion()
            , migrationInfo.getScript()
            , migrationInfo.getInstalledOn()
            , migrationInfo.getState().getDisplayName()
        );
      }
    }
  }

}
