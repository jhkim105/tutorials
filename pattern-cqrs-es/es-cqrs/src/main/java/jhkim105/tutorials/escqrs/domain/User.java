package jhkim105.tutorials.escqrs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dm_user")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @Column(length = 50)
  private String id;

  @Column(nullable = false, unique = true)
  @Setter
  private String username;

  public User(String id, String username) {
    this.id = id;
    this.username = username;
  }
}
