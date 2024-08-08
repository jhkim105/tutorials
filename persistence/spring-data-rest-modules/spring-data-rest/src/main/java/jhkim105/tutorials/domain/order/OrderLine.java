package jhkim105.tutorials.domain.order;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import jhkim105.tutorials.base.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "de_order_line")
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id")
public class OrderLine extends BaseEntity<String> {

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
  private int quantity;

  @CreatedDate
  private Instant createdDate;


}
