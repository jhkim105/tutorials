package jhkim105.tutorials.spring.data.encrypt;

import javax.persistence.AttributeConverter;
import utils.crypto.Aes;

public class StringEncryptConverter implements AttributeConverter<String, String> {


  private static final String P = "1234567890123456";

  @Override
  public String convertToDatabaseColumn(String s) {
    return Aes.getInstance(P).encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    return Aes.getInstance(P).decrypt(s);
  }
}
