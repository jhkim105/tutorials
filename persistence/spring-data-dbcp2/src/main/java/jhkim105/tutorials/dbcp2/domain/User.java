package jhkim105.tutorials.dbcp2.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "dm_user")
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

  @Builder
  public User(String name) {
    this.name = name;
  }
}
