package jhkim105.tutorials.domain.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jhkim105.tutorials.base.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
import jhkim105.tutorials.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "de_order")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Order extends BaseEntity<String> {


  private static final long serialVersionUID = -5361562237048010181L;

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = ColumnLengths.UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "order")
  @Exclude
  private Set<OrderProduct> orderProducts = new HashSet<>();

  @CreatedDate
  private ZonedDateTime orderDate;

  @LastModifiedDate
  private ZonedDateTime updatedDate;

  @Builder
  public Order(User user, Set<OrderProduct> orderProducts) {
    this.user = user;
    this.orderProducts = orderProducts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Order order = (Order) o;

    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return 737800560;
  }
}
