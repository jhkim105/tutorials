package jhkim105.tutorials.spring.data.encrypt.crypto;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
@RequiredArgsConstructor
public class StringEncryptConverter implements AttributeConverter<String, String> {

  private final Crypto crypto;

  @Override
  public String convertToDatabaseColumn(String s) {
    return crypto.encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    if (s == null)
      return null;
    return crypto.decrypt(s);
  }

}
