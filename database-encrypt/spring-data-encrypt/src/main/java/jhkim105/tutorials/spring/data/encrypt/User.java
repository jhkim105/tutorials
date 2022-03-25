package jhkim105.tutorials.spring.data.encrypt;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  @ColumnTransformer(
      read = "cast(AES_DECRYPT(UNHEX(username), fn_enckey()) as CHAR)",
      write = "HEX(AES_ENCRYPT(?, fn_enckey()))")
  private String username;

  @Column
  private String description;

  @Column
  @Convert(converter = StringEncryptConverter.class)
  private String phoneNumber;

  @Builder
  public User(String name, String username, String description, String phoneNumber) {
    this.name = name;
    this.username = username;
    this.description = description;
    this.phoneNumber = phoneNumber;
  }
}
