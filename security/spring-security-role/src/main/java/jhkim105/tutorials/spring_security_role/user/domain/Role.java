package jhkim105.tutorials.spring_security_role.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tu_role")
@EqualsAndHashCode(callSuper = false, of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class Role {
  @Id
  @Column(name = "id", length = 50)
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @GeneratedValue(generator = "system-uuid")
  private String id;

  private String name;
  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ElementCollection(targetClass = Privilege.class)
  @JoinTable(name = "tu_role_privileges", joinColumns = {@JoinColumn(name = "role_id")})
  @Column(name = "privilege", nullable = false)
  @Enumerated(EnumType.STRING)
  private Collection<Privilege> privileges;

}
