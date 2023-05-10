package jhkim105.tutorials.multitenancy.crypto;

public class AesCrypto {

  private final Aes aes;
  public AesCrypto(String password) {
    aes = Aes.newInstance(password);
  }

  public String encrypt(String s) {
    return aes.encrypt(s);
  }

  public String decrypt(String s) {
    return aes.decrypt(s);
  }

}
