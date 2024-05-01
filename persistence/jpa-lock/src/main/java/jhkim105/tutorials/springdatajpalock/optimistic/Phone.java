package jhkim105.tutorials.springdatajpalock.optimistic;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLock;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Phone {

  @Id
  @GeneratedValue
  private Long id;

  @Setter
  private String number;

  @Setter
  @OptimisticLock( excluded = true ) // 이 속성이 변경되어도 version을 증가시키지 않는다.
  private long callCount;

  @Version
  private Instant version;

  public Phone(String number) {
    this.number = number;
  }
}
