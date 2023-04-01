package jhkim105.tutorials.spring.data.encrypt.crypto;

import utils.crypto.Aes;

public class AesCrypto implements Crypto {

  private final Aes aes;
  public AesCrypto(String password) {
    aes = Aes.newInstance(password);
  }

  @Override
  public String encrypt(String s) {
    return aes.encrypt(s);
  }

  @Override
  public String decrypt(String s) {
    return aes.decrypt(s);
  }

}
