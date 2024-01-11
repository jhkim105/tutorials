package jhkim105.tutorials.spring.security.jwt.user;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tu_user")
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"password"})
@Slf4j
public class User {
  @Id
  @Column(name = "id", length = 50)
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @GeneratedValue(generator = "system-uuid")
  private String id;

  private String username;

  private String password;

  private String nickname;

  @ElementCollection(targetClass = Role.class)
  @JoinTable(name = "tu_user_roles", joinColumns = {@JoinColumn(name = "user_id")})
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  public void update(String nickname) {
    this.nickname = nickname;
  }
}
