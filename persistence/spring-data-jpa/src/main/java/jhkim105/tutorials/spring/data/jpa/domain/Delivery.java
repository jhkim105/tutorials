package jhkim105.tutorials.spring.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import jhkim105.tutorials.spring.data.jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "de_delievery")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity<String> {
  @Id
  @GeneratedValue(generator = "uuid")
  @Column(length = ColumnLengths.UUID)
  private String id;

  @Embedded
  private Address address;

  @Enumerated(value = EnumType.STRING)
  private DeliveryStatus status;

  public enum DeliveryStatus {
    READY, ING, COMPLETE
  }
}
