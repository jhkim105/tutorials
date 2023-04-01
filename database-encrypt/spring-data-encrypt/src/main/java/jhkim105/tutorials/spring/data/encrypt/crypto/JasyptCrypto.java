package jhkim105.tutorials.spring.data.encrypt.crypto;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;

@RequiredArgsConstructor
public class JasyptCrypto implements Crypto {

  private final StringEncryptor encryptor;

  @Override
  public String encrypt(String s) {
    return encryptor.encrypt(s);
  }

  @Override
  public String decrypt(String s) {
    return encryptor.decrypt(s);
  }

}
