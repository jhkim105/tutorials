package jhkim105.tutorials.domain.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import jhkim105.tutorials.base.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "de_product")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class Product extends BaseEntity<String> {

  private static final long serialVersionUID = 499044826319600659L;
  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Long price;


  @Builder
  public Product(String name, Long price) {
    this.name = name;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Product product = (Product) o;

    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return 2042274511;
  }
}
