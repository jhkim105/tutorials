package jhkim105.tutorials.axon.queries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "dm_order")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

  @Id
  @Column(length = 50)
  private String id;

  @ElementCollection
  @JoinTable(name = "dm_order_products")
  private Map<String, Integer> products = new HashMap<>();

  private OrderStatus orderStatus;

  public Order(String id) {
    this.id = id;
    orderStatus = OrderStatus.CREATED;
  }


}