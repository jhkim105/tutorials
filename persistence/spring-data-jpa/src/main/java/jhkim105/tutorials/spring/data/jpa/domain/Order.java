package jhkim105.tutorials.spring.data.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "de_order")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order extends BaseEntity<String> {

  @Id
  @UuidGenerator
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Column(nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @CreatedDate
  private LocalDateTime orderDate;

  @OneToMany(mappedBy = "order")
  @Exclude
  private Set<OrderProduct> orderProducts = new HashSet<>();

  @OneToOne
  private Delivery delivery;

  public void setOrderProducts(Set<OrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
  }


  @Builder
  public Order(String name, User user, Set<OrderProduct> orderProducts) {
    this.name = name;
    this.user = user;
    this.orderProducts = orderProducts;
  }

}
