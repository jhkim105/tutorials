package jhkim105.tutorials.multitenancy.tenant;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TenantDatabaseHelperTest {

  @Autowired
  TenantDatabaseHelper tenantDatabaseHelper;

  @Test
  void getTenantDatabaseNames() {
    List<String> databaseNames = tenantDatabaseHelper.getTenantDatabaseNames();
    log.debug("{}", databaseNames);
  }


}