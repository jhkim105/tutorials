package jhkim105.tutorials.spring.data.jpa.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jhkim105.tutorials.spring.data.jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "dm_user")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity<String> {
  @Id
  @GeneratedValue(generator = "uuid")
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

  @Builder
  public User(String username) {
    this.username = username;
  }

  @ManyToOne
  private Group group;

}
