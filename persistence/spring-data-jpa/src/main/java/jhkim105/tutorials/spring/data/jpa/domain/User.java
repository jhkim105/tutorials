package jhkim105.tutorials.spring.data.jpa.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "dm_user")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity<String> {
  @Id
  @UuidGenerator
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Column(nullable = false)
  private String username;

  @ElementCollection
  @CollectionTable(name = "dm_user_address", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"))
  @Exclude
  private Set<Address> addresses = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<Order> orders = new HashSet<>();

  public User(String username) {
    this.username = username;
  }

  @ManyToOne
  private Group group;

}
