package jhkim105.tutorials.spring.data.jpa.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import jhkim105.tutorials.spring.data.jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "de_order_products")
@Getter
@ToString
@EqualsAndHashCode(of = {"order", "product"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity<Long> {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Order order;

  @ManyToOne
  private Product product;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private int count;

  @Column(name = "delivery_date")
  private LocalDateTime deliveryDate;

}
