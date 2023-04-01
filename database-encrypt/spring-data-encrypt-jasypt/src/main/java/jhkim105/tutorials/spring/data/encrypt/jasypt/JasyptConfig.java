package jhkim105.tutorials.spring.data.encrypt.jasypt;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class JasyptConfig {

  private static final String DP = "secret#01";

  @Bean
  public CustomPBStringEncryptor customPBStringEncryptor() {
    PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
    pooledPBEStringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    pooledPBEStringEncryptor.setPassword(getPassword());
    pooledPBEStringEncryptor.setPoolSize(4); // This would be a good value for a 4-core system
    CustomPBStringEncryptor customPBStringEncryptor = new CustomPBStringEncryptor(pooledPBEStringEncryptor);
    return customPBStringEncryptor;
  }

  private String getPassword() {
    String password = System.getProperty("jasypt.encryptor.password");
    if (StringUtils.hasText(password)) {
      log.info("jasypt.encryptor.password:{}", password);
      return password;
    }
    return DP;
  }

  @Bean
  public HibernatePBEStringEncryptor hibernateStringEncryptor(CustomPBStringEncryptor customPBStringEncryptor) {
    HibernatePBEStringEncryptor hibernateStringEncryptor = new HibernatePBEStringEncryptor();
    hibernateStringEncryptor.setRegisteredName("hibernateStringEncryptor");
    hibernateStringEncryptor.setEncryptor(customPBStringEncryptor);
    return hibernateStringEncryptor;
  }

}
