package jhkim105.tutorials.domain.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Objects;
import jhkim105.tutorials.base.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
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
