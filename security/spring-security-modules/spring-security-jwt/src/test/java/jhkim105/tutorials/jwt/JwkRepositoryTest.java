package jhkim105.tutorials.jwt;

import jhkim105.tutorials.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JwkRepositoryTest {

    @Autowired
    private JwkRepository jwkRepository;


    @Test
    void findTopByOrderByCreatedAtDesc() {
        jwkRepository.findTopByOrderByCreatedAtDesc();
    }
}