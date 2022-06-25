package jhkim105.tutorials.multitenancy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private String description;


  @Builder
  public User(String name, String username, String password, String description) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.description = description;
  }
}
