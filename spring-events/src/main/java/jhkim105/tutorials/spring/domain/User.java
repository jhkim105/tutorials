package jhkim105.tutorials.spring.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "dm_user")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String name;

  @Builder
  public User(String name) {
    this.name = name;
  }
}
