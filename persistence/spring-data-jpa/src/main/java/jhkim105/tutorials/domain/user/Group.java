package jhkim105.tutorials.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "dm_group")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "group")
  private Set<User> users = new HashSet<>();

  public Group(String name) {
    this.name = name;
  }
}
