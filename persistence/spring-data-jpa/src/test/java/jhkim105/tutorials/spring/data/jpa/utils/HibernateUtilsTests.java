package jhkim105.tutorials.spring.data.jpa.utils;

import org.junit.jupiter.api.Test;

class HibernateUtilsTests {


  @Test
  void generateSchema() {
    HibernateUtils.generateSchema("jdbc:mariadb://localhost:3306/demo_1?createDatabaseIfNotExist=true", "root", "111111",
        "jhkim105.tutorials.spring.data.jpa.domain");
  }

}
