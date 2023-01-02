package jhkim105.tutorials.spring.jasypt;


import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JasyptTest {

  @Autowired
  private StringEncryptor stringEncryptor;

  @Value("${jasypt.encryptor.password}")
  private String jasyptPassword;
  private StringEncryptor defaultStringEncryptor;
  private StringEncryptor customStringEncryptor;

  @BeforeEach
  void setup() {
    defaultStringEncryptor = defaultStringEncryptor();
    customStringEncryptor = customStringEncryptor();
  }

  @Test
  void encryptAndDecrypt() {
    String planText = "guest";
    String enc = stringEncryptor.encrypt(planText);
    assertThat(customStringEncryptor.decrypt(enc)).isEqualTo(planText);
  }

  private StringEncryptor defaultStringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    config.setPassword(jasyptPassword); //System.setProperty("jasypt.encryptor.password", "secret1234")
    config.setKeyObtentionIterations("1000");
    config.setPoolSize(1);
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    return encryptor;
  }

  private StringEncryptor customStringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(jasyptPassword);
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