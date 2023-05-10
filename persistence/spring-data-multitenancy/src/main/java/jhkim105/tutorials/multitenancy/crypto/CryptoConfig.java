package jhkim105.tutorials.multitenancy.crypto;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfig {

  @Bean
  public AesCrypto aesCrypto() {
    return new AesCrypto("secret#01");
  }

}
