package jhkim105.tutorials.spring_security_role.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tu_user")
@EqualsAndHashCode(callSuper = false, of = "username")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"password"})
@Slf4j
public class User implements Serializable {
  @Id
  @Column(name = "id", length = 50)
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @GeneratedValue(generator = "system-uuid")
  private String id;

  @Column(length = 200, unique = true)
  private String username;

  private String password;

  @ManyToMany
  @JoinTable(name = "tu_user_roles", joinColumns = {@JoinColumn(name = "user_id")})
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Collection<Role> roles;


  @Builder
  public User(String username, String password, Collection<Role> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }
}
