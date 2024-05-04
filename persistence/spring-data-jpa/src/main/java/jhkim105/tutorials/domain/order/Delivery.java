package jhkim105.tutorials.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jhkim105.tutorials.domain.Address;
import jhkim105.tutorials.domain.BaseEntity;
import jhkim105.tutorials.domain.ColumnLengths;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "de_delievery")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity<String> {
  @Id
  @UuidGenerator
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Embedded
  private Address address;

  @Enumerated(value = EnumType.STRING)
  @Column(length = 10)
  private DeliveryStatus status;

  public enum DeliveryStatus {
    READY, ING, COMPLETE
  }
}
