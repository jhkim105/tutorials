package jhkim105.tutorials.multitenancy.crypto;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
//@NoArgsConstructor
public class StringEncryptConverter implements AttributeConverter<String, String> {

  private AesCrypto crypto;

  public StringEncryptConverter(AesCrypto crypto) {
    this.crypto = crypto;
  }

  @Override
  public String convertToDatabaseColumn(String s) {
    return crypto.encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    if (s == null) {
      return null;
    }
    return crypto.decrypt(s);
  }


}
