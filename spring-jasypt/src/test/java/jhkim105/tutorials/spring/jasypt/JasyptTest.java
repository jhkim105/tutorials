package jhkim105.tutorials.spring.jasypt;


import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JasyptTest {

  private StringEncryptor stringEncryptor;

  @BeforeEach
  void setup() {
    stringEncryptor = stringEncryptor();
  }

  @Test
  void encrypt() {
    log.info("{}", stringEncryptor.encrypt("guest"));
  }

  @Test
  void decrypt() {
    log.info("{}", stringEncryptor.decrypt("WQX4VM86v6/Lo2G/ptjECA=="));
  }

  private StringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword("rm#2020");
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize(1);
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    return encryptor;
  }
}