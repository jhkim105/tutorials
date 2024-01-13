package jhkim105.tutorials.spring.data.jpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "de_order_products")
@Getter
@ToString
@EqualsAndHashCode(of = {"order", "product"}, callSuper = false)
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
