package jhkim105.tutorials.springdatajpalock.optimistic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@OptimisticLocking(type = OptimisticLockType.NONE)
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptimisticNone {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @GeneratedValue(generator = "uuid")
  @Column(length = 50)
  private String id;

  @ColumnDefault("0")
  @Column(nullable = false)
  private long count;

  @Version
  private Integer version;

  public void incr() {
    count++;
  }



}
