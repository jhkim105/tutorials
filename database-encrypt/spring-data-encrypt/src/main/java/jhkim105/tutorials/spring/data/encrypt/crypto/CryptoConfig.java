package jhkim105.tutorials.spring.data.encrypt.crypto;


import jhkim105.tutorials.spring.data.encrypt.ApplicationProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfig {


  @Bean
  @ConditionalOnProperty(name = "application.database-encrypt-type", havingValue = "AES", matchIfMissing = true)
  public Crypto aesCrypto(ApplicationProperties applicationProperties) {
    return new AesCrypto(applicationProperties.getDatabaseEncryptPassword());
  }

  @Bean
  @ConditionalOnProperty(name = "application.database-encrypt-type", havingValue = "JASYPT")
  public Crypto jasyptCrypto(ApplicationProperties applicationProperties) {
    return new JasyptCrypto(customPBStringEncryptor(applicationProperties.getDatabaseEncryptPassword()));
  }

  private CustomPBStringEncryptor customPBStringEncryptor(String password) {
    PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
    pooledPBEStringEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
    pooledPBEStringEncryptor.setPassword(password);
    pooledPBEStringEncryptor.setPoolSize(4); // This would be a good value for a 4-core system
    return new CustomPBStringEncryptor(pooledPBEStringEncryptor);
  }

}
