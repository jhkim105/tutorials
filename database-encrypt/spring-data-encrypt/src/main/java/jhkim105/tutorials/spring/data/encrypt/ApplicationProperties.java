package jhkim105.tutorials.spring.data.encrypt;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplicationProperties {

  private DatabaseEncryptType databaseEncryptType;

  private String databaseEncryptPassword;

  public enum DatabaseEncryptType {
    AES, JASYPT
  }
}
