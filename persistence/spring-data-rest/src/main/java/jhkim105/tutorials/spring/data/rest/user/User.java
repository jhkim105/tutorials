package jhkim105.tutorials.spring.data.rest.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jhkim105.tutorials.spring.data.rest.base.BaseEntity;
import jhkim105.tutorials.spring.data.rest.domain.ColumnLengths;
import jhkim105.tutorials.spring.data.rest.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "dm_user")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class User extends BaseEntity<String> {

  private static final long serialVersionUID = -9023560868694292282L;

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Column(nullable = false)
  private String name;

  @NaturalId
  @Column(nullable = false)
  private String username;

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<Order> orders = new HashSet<>();


  @Builder
  public User(String username, String name) {
    this.username = username;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;

    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(username);
  }
}
