package jhkim105.tutorials.spring.data.jpa.nplusone.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Table(name = "t_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@NamedEntityGraph(name = "User.coupons",
    attributeNodes = @NamedAttributeNode("coupons")
)
@NamedEntityGraph(name = "User.couponsAndOrders",
    attributeNodes = {@NamedAttributeNode("coupons"), @NamedAttributeNode("orders")}

)
public class User {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<Order> orders = new HashSet<>();

  @OneToMany(mappedBy = "user")
  @Exclude
  private Set<Coupon> coupons = new HashSet<>();

}
