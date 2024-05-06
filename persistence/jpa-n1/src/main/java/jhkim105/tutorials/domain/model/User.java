package jhkim105.tutorials.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "t_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "User.orders",
    attributeNodes = {@NamedAttributeNode("orders"), @NamedAttributeNode("membership")}
)
@NamedEntityGraph(name = "User.ordersAndCoupons",
    attributeNodes = {@NamedAttributeNode("orders"), @NamedAttributeNode("coupons"), @NamedAttributeNode("membership")}

)
public class User {

  @Id
  @UuidGenerator
  @Column(length = 50)
  private String id;

  private String name;

  @OneToMany(mappedBy = "user")
  private Set<Order> orders = new HashSet<>();

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<Coupon> coupons = new HashSet<>();


  //  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
  @OneToOne(mappedBy = "user")
  private Membership membership;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
  private UserProfile profile;

}
