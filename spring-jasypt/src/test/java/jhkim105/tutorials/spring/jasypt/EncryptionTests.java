package jhkim105.tutorials.spring.jasypt;

import static com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties.bindConfigProps;

import com.ulisesbocchio.jasyptspringboot.configuration.StringEncryptorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mock.env.MockEnvironment;


@Slf4j
class EncryptionTests {

  @Disabled("암호화 필요시에 실행")
  @Test
  void encrypt() {
    log.debug(stringEncryptor().encrypt("abc123"));
  }

  @Disabled
  @Test
  public void decrypt() {
    log.debug(stringEncryptor().decrypt("gWTrsdJcVdPUGfyjCCeclopFRO1D6DucsdYAQOeFVPHQ9WRvw2XN/kLTk5LFHcoK"));
  }

  private StringEncryptor stringEncryptor() {
    ConfigurableEnvironment mockEnvironment = new MockEnvironment()
        .withProperty("jasypt.encryptor.password", "secret1234");
    return new StringEncryptorBuilder(bindConfigProps(mockEnvironment), "jasypt.encryptor")
        .build();
  }
}
