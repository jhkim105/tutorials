package jhkim105.tutorials.spring.data.rest.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import jhkim105.tutorials.spring.data.rest.base.BaseEntity;
import jhkim105.tutorials.spring.data.rest.domain.ColumnLengths;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "de_order_products")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class OrderProduct extends BaseEntity<String> {

  private static final long serialVersionUID = -4083775078900703902L;

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = ColumnLengths.UUID)
  private String id;

  @NaturalId
  @ManyToOne(optional = false)
  private Order order;

  @NaturalId
  @ManyToOne(optional = false)
  private Product product;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private int count;

  @CreatedDate
  private ZonedDateTime createdDate;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    OrderProduct that = (OrderProduct) o;

    if (!Objects.equals(order, that.order)) {
      return false;
    }
    return Objects.equals(product, that.product);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(order);
    result = 31 * result + (Objects.hashCode(product));
    return result;
  }
}
