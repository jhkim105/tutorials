package jhkim105.tutorials.domain.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jhkim105.tutorials.base.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "de_product")
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
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

}
